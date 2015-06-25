package com.zx.pinke.util;

public class AppConfig {

	public static class NetWork{
		
		public static class IV{
//			private static final String BASE_SERVER_ADDRESS = "http://192.168.1.141:8080/PinkeServer";
			private static final String BASE_SERVER_ADDRESS = "http://116.252.185.62:8080/PinkeServer";
			
			public static final String PRE_SERVER_ADDRESS = BASE_SERVER_ADDRESS;
			
			public static final String URL_PUBLIC_SERVICE = BASE_SERVER_ADDRESS + "/share/getList";
			public static final String URL_ACCOUNT_LOGIN = BASE_SERVER_ADDRESS + "/iv/login";
			public static final String URL_ACCOUNT_REGISTER = BASE_SERVER_ADDRESS + "/iv/regist";
			public static final String URL_ACCOUNT_ALTER_INFO = BASE_SERVER_ADDRESS + "/iv/alterInfo";
			public static final String URL_ACCOUNT_ALTER_PWD = BASE_SERVER_ADDRESS + "/iv/alterPwd";
			
			public static final String URL_SERVICE_JOIN = BASE_SERVER_ADDRESS + "/share/join";
			public static final String URL_SERVICE_ADD = BASE_SERVER_ADDRESS + "/share/addShare";
			public static final String URL_SERVICE_USERLIST = BASE_SERVER_ADDRESS + "/share/getUserList";
			public static final String URL_SERVICE_QUIT = BASE_SERVER_ADDRESS + "/share/quit";
			public static final String URL_SERVICE_UNPUBLISH = BASE_SERVER_ADDRESS + "/share/unpublish";
			

		}
	}
	
	public static class APN{
		public static class ChinaMobile{
			public static final String MMSC_ADDRESS = "http://mmsc.montemet.com";
			public static final String POLICY = "10.0.0.172";
			public static final String PORT = "80";
		}
		
		public static class ChinaUnicom{
			public static final String MMSC_ADDRESS = "http://mmsc.myuni.com.cn";
			public static final String POLICY = "010.000.000.172";
			public static final String PORT = "80";
		}
		public static class ChinaTelcom{
			public static final String MMSC_ADDRESS = "http://mmsc.vnet.mobi";
			public static final String POLICY = "10.0.0.200";
			public static final String PORT = "80";
		}
	}
	
	
}
