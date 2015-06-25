package com.zx.pinke.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.zx.pinke.util.HttpJsonResult.ResultCode;

/**
 * 文件下载工具类
 * @author lintp
 *
 */
public class DownloadHelper {
	
	private static final String TAG = "DownloadHelper";
	
	/**
	 * 文件下载
	 * @param listener 已下载字节大小
	 * @return
	 */
	public static HttpFileResult download(Context context,URI uri,String sessionId,ByteSizesHandleListener bsl,InterruptHandleListener ihl){
		System.out.println("download....");
		HttpFileResult result = new HttpFileResult();
		if(!netWorkAvaiable(context)){
			result.setResultCode(ResultCode.CODE_FAIL_NO_NETWORK);
			System.out.println("download....1");
			return result;
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		try {
//			String sessionId = AppPreference.getString(AppPreference.PREF_KEY_SESSION_ID, "");
			if(!TextUtils.isEmpty(sessionId)){
				httpGet.setHeader("Cookie", "JSESSIONID="+sessionId);
			}
			
			HttpResponse response = httpClient.execute(httpGet);
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				System.out.println("download....2");
				HttpEntity entity = response.getEntity();
				long length = entity.getContentLength();
				Header[] headers = response.getAllHeaders();
				String fileName = "Mms.apk";
				for(Header h : headers){
					if("Content-Disposition".equals(h.getName())){
						String value = h.getValue();
						fileName = value.substring(value.indexOf("=")+1);
						break;
					}
				}
				
				FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
				InputStream is = entity.getContent();
				
				try{
					byte[] buffer = new byte[1024*100];
					int i = 0;
					long size = 0;
					while((i = is.read(buffer))!=-1){
						fos.write(buffer, 0, i);
						size +=i;
						if(bsl != null){
							bsl.execute(length,size);
							if(ihl.interrupt()){
								result.setResultCode(ResultCode.CODE_SUCCESS);
								return result;
							}
							
						}
					}
					fos.flush();
					
					entity.consumeContent();
					
					result.setResultCode(ResultCode.CODE_SUCCESS);
					result.setFile(context.getFileStreamPath(fileName));
					System.out.println("download....6");
					return result;
					
				}catch(Exception e){System.out.println("download....3");
					result.setResultCode(ResultCode.CODE_FAIL_CONNECT_SERVER);
					return result;
				}finally{
					if(fos != null) fos.close();
					if(is != null) is.close();
				}
			}else{System.out.println("download....4");
				result.setResultCode(ResultCode.CODE_FAIL_CONNECT_SERVER);
				return result;
			}
			
		} catch (Exception e) {System.out.println("download....5");
			Log.e(TAG, "请求服务失败->"+e.getMessage());
			result.setResultCode(ResultCode.CODE_FAIL_CONNECT_SERVER);
			return result;
		}
		
	}
	
	private boolean uploadFile(String actionUrl, String uploadFile, String newName) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + newName + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 关闭DataOutputStream */
			ds.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static HttpFileResult download(Context context,URI uri,String sessioId){
		return download(context,uri,null,null,null);
	}

	/**
	 * 下载字节大小
	 * @author lintp
	 *
	 */
	public interface ByteSizesHandleListener{
		public void execute(long totalLength,long size);
	}
	
	public interface InterruptHandleListener{
		public boolean interrupt();
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
