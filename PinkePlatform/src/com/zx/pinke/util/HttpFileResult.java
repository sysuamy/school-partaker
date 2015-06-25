package com.zx.pinke.util;

import java.io.File;

import org.apache.http.cookie.Cookie;

public class HttpFileResult {

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
	private File mFile;
	
	public boolean success(){
		return mResultCode == ResultCode.CODE_SUCCESS;
	}

	public File getFile() {
		return mFile;
	}

	public void setFile(File mFile) {
		this.mFile = mFile;
	}

	public int getResultCode() {
		return mResultCode;
	}

	public void setResultCode(int mResultCode) {
		this.mResultCode = mResultCode;
	}
	
}
