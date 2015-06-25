package com.zx.pinke.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelecomUtil {

	/**
	 * -1��δ֪
	 * 0���й��ƶ�
	 * 1���й���ͨ
	 * 2���й�����
	 * @param context
	 * @return
	 */
	public static int getTelcomNetType(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		/** ��ȡSIM����IMSI�� 
        * SIM��Ψһ��ʶ��IMSI �����ƶ��û�ʶ���루IMSI��International Mobile Subscriber Identification Number���������ƶ��û��ı�־�� 
        * ������SIM���У������������ƶ��û�����Ч��Ϣ��IMSI��MCC��MNC��MSIN��ɣ�����MCCΪ�ƶ����Һ��룬��3λ������ɣ� 
        * Ψһ��ʶ���ƶ��ͻ������Ĺ��ң��ҹ�Ϊ460��MNCΪ����id����2λ������ɣ� 
        * ����ʶ���ƶ��ͻ����������ƶ����磬�й��ƶ�Ϊ00���й���ͨΪ01,�й�����Ϊ03��MSINΪ�ƶ��ͻ�ʶ���룬���õȳ�11λ���ֹ��ɡ� 
        * Ψһ��ʶ�����GSM�ƶ�ͨ�������ƶ��ͻ�������Ҫ�������ƶ�������ͨ��ֻ��ȡ��SIM���е�MNC�ֶμ��� 
        */  
       String imsi = tm.getSubscriberId();  
	   if (imsi != null) {
		   // ��Ϊ�ƶ�������46000�µ�IMSI�Ѿ����꣬����������һ��46002��ţ�134/159�Ŷ�ʹ���˴˱��
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				return 0;
				// �й��ƶ�
			} else if (imsi.startsWith("46001")) {
				return 1;
				// �й���ͨ
			} else if (imsi.startsWith("46003")) {
				// �й�����
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
		if(Pattern.matches(VALID_NUMBER_REGEX, number)){//����: () + - 0-9
			Matcher marcher = DIGIT_PATTERN.matcher(number);//���뺬����
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
