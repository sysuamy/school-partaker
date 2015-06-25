package com.zx.pinke.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelecomUtil {

	/**
	 * -1：未知
	 * 0：中国移动
	 * 1：中国联通
	 * 2：中国电信
	 * @param context
	 * @return
	 */
	public static int getTelcomNetType(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		/** 获取SIM卡的IMSI码 
        * SIM卡唯一标识：IMSI 国际移动用户识别码（IMSI：International Mobile Subscriber Identification Number）是区别移动用户的标志， 
        * 储存在SIM卡中，可用于区别移动用户的有效信息。IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成， 
        * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成， 
        * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。 
        * 唯一地识别国内GSM移动通信网中移动客户。所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可 
        */  
       String imsi = tm.getSubscriberId();  
	   if (imsi != null) {
		   // 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				return 0;
				// 中国移动
			} else if (imsi.startsWith("46001")) {
				return 1;
				// 中国联通
			} else if (imsi.startsWith("46003")) {
				// 中国电信
				return 2;
			}
	    }
	   return -1;
	}
	
	public static String getImsi(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        return imsi;
	}
	
	public static String getImei(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;
	}
	
	private static Pattern DIGIT_PATTERN = Pattern.compile("\\d*");
	private static String VALID_NUMBER_REGEX = "[()+\\-0-9]+";
	public static boolean isValidCellPhoneNumer(String number){
		if(Pattern.matches(VALID_NUMBER_REGEX, number)){//包含: () + - 0-9
			Matcher marcher = DIGIT_PATTERN.matcher(number);//必须含数字
			int count = 0;
			while(marcher.find()){
				count = count + marcher.group().length();
				if(count==11)
					return true;
			}
		}
		return false;
	}
}
