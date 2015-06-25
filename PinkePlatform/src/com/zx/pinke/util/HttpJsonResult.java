package com.zx.pinke.util;

import org.apache.http.cookie.Cookie;
import org.json.JSONObject;

public class HttpJsonResult {

	public interface ResultCode {

		//�����
		public static final int CODE_SUCCESS				= 0;//�ɹ�
		public static final int CODE_FAIL_NO_NETWORK 		= 1;//û����������
		public static final int CODE_FAIL_CONNECT_SERVER	= 2;//��������ʧ��
		public static final int CODE_FAIL_PARSER			= 3;//��������ʧ��
		public static final int CODE_FAIL_AUTH				= 4;//�û�δ��֤
	}
	
	private int mResultCode = -1;
	private Cookie mCookie;//Ԥ������
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
