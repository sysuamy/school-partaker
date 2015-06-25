package com.zx.pinke.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreference {
	private static final String PREF_NAME = "mms_pref";
	private static SharedPreferences sp = null;
	
	public static final String PREF_KEY_APP_FIRST_INSTALL = "app_first_install";//是否首次安装
	public static final String PREF_KEY_APP_VERSION	= "app_version";//应用版本号,用于升级时匹配
	public static final String PREF_KEY_APP_INIT	= "app_init";
	public static final String PREF_KEY_APP_INIT_APPDB = "app_init_appdb";//初始化数据库
	public static final String PREF_KEY_COMPANY_SWITCH	= "company_switch";
	public static final String PREF_KEY_LAST_UPDATE_TIME_CLASSIC_CATEGORY	= "last_update_time_classic_category";
//	public static final String PREF_KEY_LAST_UPDATE_TIME_CLASSIC_MMS = "last_update_time_classic_mms";
	
	public static final String PREF_KEY_TELCOM_NET_TYPE = "telcom_net_type";//运营商网络类型
	public static final String PREF_KEY_BRAND_OF_CHINA_MOBILE = "brand_of_china_mobile";//移动品牌
	public static final String PREF_KEY_IMSI = "imsi";
	public static final String PREF_KEY_ACCOUNT = "account";//用户号码
	public static final String PREF_KEY_APK_VERSION_DETECT = "apk_version_detect";//版本检测时间标记
	
	public static final String PREF_KEY_HAS_RECORD_APK_INSTALL_INFO = "has_record_apk_install_info";//是否已经向服务端记录安装信息
	
	public static final String PREF_KEY_SESSION_ID = "session_id";//与服务端同步会话ID
	
	public static final String PREF_KEY_LAST_BACKUP_TIME = "last_backup_time";
	
	public static final String PREF_KEY_CHANNEL_ID = "channel_id";
	
	public static final String PREF_KEY_HEART_TIME = "heart_time";
	
	public static final String PREF_KEY_BATCH_GROUP_REPLY_TIME = "batch_group_reply_time";
	
	public class IV{
		public static final String PREF_KEY_ACCOUNT = "iv_account";//i志愿账户名，退出清空帐号信息
	}
	
	public static void init(Context context){
		sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		System.out.println("------->" + sp);
		resetPrefInfo(context);
	}
	
	//换卡情况下，重置局部配置信息
	private static void resetPrefInfo(Context context){
		String prefImsi = getString(PREF_KEY_IMSI,"");
		String imsi = TelecomUtil.getImsi(context);
		if(!prefImsi.equals(imsi)){
			putString(PREF_KEY_IMSI, imsi);
			putInt(PREF_KEY_TELCOM_NET_TYPE,TelecomUtil.getTelcomNetType(context));
			putInt(PREF_KEY_BRAND_OF_CHINA_MOBILE,-1);
			putString(PREF_KEY_ACCOUNT, "");
		}
	}
	
	
	public static synchronized String getString(String key,String defValue){
		return sp.getString(key, defValue);
	}
	
	public static synchronized void putString(String key,String value){
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static synchronized int getInt(String key,int defValue){
		return sp.getInt(key, defValue);
	}
	
	public static synchronized void putInt(String key,int value){
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static synchronized long getLong(String key,long defValue){
		return sp.getLong(key, defValue);
	}
	
	public static synchronized void putLong(String key,long value){
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static synchronized boolean getBoolean(String key,boolean defValue){
		return sp.getBoolean(key, defValue);
	}
	
	public static synchronized void putBoolean(String key,Boolean value){
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
}
