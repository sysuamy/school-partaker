package com.zx.pinke.util;

import org.apache.http.cookie.Cookie;
import org.json.JSONObject;

public class HttpJsonResult {

	public interface ResultCode {

		//结果码
		public static final int CODE_SUCCESS				= 0;//成功
		public static final int CODE_FAIL_NO_NETWORK 		= 1;//没有链接网络
		public static final int CODE_FAIL_CONNECT_SERVER	= 2;//请求服务端失败
		public static final int CODE_FAIL_PARSER			= 3;//解析数据失败
		public static final int CODE_FAIL_AUTH				= 4;//用户未认证
	}
	
	private int mResultCode = -1;
	private Cookie mCookie;//预留备用
	private String mSessionId;
	private JSONObject mJsonResult;
	
	public void setResultCode(int mResultCode) {
		this.mResultCode = mResultCode;
	}

	public void setJsonResult(JSONObject jsonResult) {
		this.mJsonResult = jsonResult;
	}

	public JSONObject getJsonResult() {
		return mJsonResult;
	}

	public int getResultCode() {
		return mResultCode;
	}

	public boolean success(){
		return mResultCode == ResultCode.CODE_SUCCESS;
	}

	public String getSessionId() {
		return mSessionId;
	}

	public void setSessionId(String sessionId) {
		this.mSessionId = sessionId;
	}

	@Override
	public String toString() {
		return "HttpJsonResult [mResultCode=" + mResultCode + ", mCookie="
				+ mCookie + ", mSessionId=" + mSessionId + ", mJsonResult="
				+ mJsonResult + "]";
	}

}
