package com.zx.pinke.util;

import java.io.File;

import org.apache.http.cookie.Cookie;

public class HttpFileResult {

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
