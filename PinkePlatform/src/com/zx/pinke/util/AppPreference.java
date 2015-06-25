package com.zx.pinke.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreference {
	private static final String PREF_NAME = "mms_pref";
	private static SharedPreferences sp = null;
	
	public static final String PREF_KEY_APP_FIRST_INSTALL = "app_first_install";//�Ƿ��״ΰ�װ
	public static final String PREF_KEY_APP_VERSION	= "app_version";//Ӧ�ð汾��,��������ʱƥ��
	public static final String PREF_KEY_APP_INIT	= "app_init";
	public static final String PREF_KEY_APP_INIT_APPDB = "app_init_appdb";//��ʼ�����ݿ�
	public static final String PREF_KEY_COMPANY_SWITCH	= "company_switch";
	public static final String PREF_KEY_LAST_UPDATE_TIME_CLASSIC_CATEGORY	= "last_update_time_classic_category";
//	public static final String PREF_KEY_LAST_UPDATE_TIME_CLASSIC_MMS = "last_update_time_classic_mms";
	
	public static final String PREF_KEY_TELCOM_NET_TYPE = "telcom_net_type";//��Ӫ����������
	public static final String PREF_KEY_BRAND_OF_CHINA_MOBILE = "brand_of_china_mobile";//�ƶ�Ʒ��
	public static final String PREF_KEY_IMSI = "imsi";
	public static final String PREF_KEY_ACCOUNT = "account";//�û�����
	public static final String PREF_KEY_APK_VERSION_DETECT = "apk_version_detect";//�汾���ʱ����
	
	public static final String PREF_KEY_HAS_RECORD_APK_INSTALL_INFO = "has_record_apk_install_info";//�Ƿ��Ѿ������˼�¼��װ��Ϣ
	
	public static final String PREF_KEY_SESSION_ID = "session_id";//������ͬ���ỰID
	
	public static final String PREF_KEY_LAST_BACKUP_TIME = "last_backup_time";
	
	public static final String PREF_KEY_CHANNEL_ID = "channel_id";
	
	public static final String PREF_KEY_HEART_TIME = "heart_time";
	
	public static final String PREF_KEY_BATCH_GROUP_REPLY_TIME = "batch_group_reply_time";
	
	public class IV{
		public static final String PREF_KEY_ACCOUNT = "iv_account";//i־Ը�˻������˳�����ʺ���Ϣ
	}
	
	public static void init(Context context){
		sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		System.out.println("------->" + sp);
		resetPrefInfo(context);
	}
	
	//��������£����þֲ�������Ϣ
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
