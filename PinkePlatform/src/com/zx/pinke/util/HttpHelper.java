package com.zx.pinke.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.zx.pinke.util.HttpFileResult.ResultCode;

public class HttpHelper {
	
	private static final String TAG = "HttpHelper";
	
	/**
	 * 用于AsyncHttpQueryHandle
	 * @param context
	 * @param uri
	 * @param params
	 * @return
	 */
	public static HttpJsonResult post(Context context,URI uri,Map<String,Object> params,String sessionId){
		HttpJsonResult result = new HttpJsonResult();
		if(!netWorkAvaiable(context)){
			result.setResultCode(ResultCode.CODE_FAIL_NO_NETWORK);
			return result;
		}
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		if(!TextUtils.isEmpty(sessionId)){
			System.out.println("指定会话ID="+sessionId);
			httpPost.setHeader("Cookie", "JSESSIONID="+sessionId);
		}
		
		
		try {
			if(params != null){
				for(String key : params.keySet()){
					System.out.println("key="+key+",value="+params.get(key));
					Object value = params.get(key);
					if(value == null) continue;
					if(value instanceof List){
						List values = (List)value;
						for(int i=0;i<values.size();i++){
							nvps.add(new BasicNameValuePair(key, values.get(i).toString()));
						}
					}else{
						nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
					}
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			CookieStore mCookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = mCookieStore.getCookies();
            for(Cookie cookie : cookies){
            	if("JSESSIONID".equalsIgnoreCase(cookie.getName())){
            		System.out.println("获得到会话ID="+cookie.getValue());
            		result.setSessionId(cookie.getValue());
            	}
            }
            
			String type = response.getFirstHeader("Content-Type").getValue();
			if(type.toLowerCase().contains("json")){
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(entity);
				JSONObject jsonRoot = new JSONObject(jsonStr);
				result.setJsonResult(jsonRoot);
				result.setResultCode(ResultCode.CODE_SUCCESS);
				return result;
			}else{
				result.setResultCode(ResultCode.CODE_FAIL_CONNECT_SERVER);
				return result;
			}
		}catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "解析JSON异常->"+e.getMessage());
			result.setResultCode(ResultCode.CODE_FAIL_PARSER);
			return result;
		}catch(Exception e){
			Log.e(TAG, "请求服务失败->"+e.getMessage());
			result.setResultCode(ResultCode.CODE_FAIL_CONNECT_SERVER);
			return result;
		}
		
	}
	
	public static HttpJsonResult post(Context context,URI uri,Map<String,Object> params){
		return post(context,uri, params,null);
	}
	
	
	/**
	 * 用于AsyncTask
	 * @param url
	 * @param params
	 * @param processer
	 * @return
	 */
	public static int post(String url,Map<String,String> params,Processer processer){
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		try {
			if(params != null){
				for(String key : params.keySet()){
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			String type = response.getFirstHeader("Content-Type").getValue();
			if(type.toLowerCase().contains("json")){
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(entity);
				JSONObject jsonRoot = new JSONObject(jsonStr);
				return processer.execute(jsonRoot);
			}else{
				return ResultCode.CODE_FAIL_CONNECT_SERVER;
			}
		}catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "解析JSON异常->"+e.getMessage());
			return ResultCode.CODE_FAIL_PARSER;
		}catch(Exception e){
			Log.e(TAG, "请求服务失败->"+e.getMessage());
			return ResultCode.CODE_FAIL_CONNECT_SERVER;
		}
		
	}
	//解析JSON
	public interface Processer{
		public int execute(JSONObject root);
	}
	
	public static boolean netWorkAvaiable(Context context){
		try{
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        if (connectivity != null) { 
	            // 获取网络连接管理的对象 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if (info != null&& info.isConnected()) { 
	                // 判断当前网络是否已经连接 
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    return true; 
	                } 
	            }
	        }
		}catch(Exception e){
		}
		return false;
	}
}
