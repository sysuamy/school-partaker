package com.zx.pinke.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.Context;

import com.zx.pinke.R;

/**
 * ���ڴ�������
 * @author lintp
 *
 */
public class DateUtil {

	private static final long MINUTE_DISTANCE = 1000*60;//����
	private static final long HOUR_DISTANCE = 1000*60*60;//Сʱ
	private static final long DAY_DISTANCE = 1000*60*60*24;//��
	private static final long DAY2_DISTANCE = 1000*60*60*24*2;//����
	
	private static final SimpleDateFormat SDF_DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SDF_DATE_MD = new SimpleDateFormat("MM-dd");
	private static final SimpleDateFormat SDF_DATE_HM = new SimpleDateFormat("HH:mm");
	
	private static final SimpleDateFormat SDF_DATE_MY = new SimpleDateFormat("MM/yy");
	
	public final static String DATE_YMD = "yyyy-MM-dd";
	public final static String DATETIME_YMDHMS = "yyyy/MM/dd HH:mm";
	public final static String DATETIME_HMS = "HH:mm:ss";
	public final static String DATETIME_HM = "HH:mm";
	/**
	 * ��ǰʱ��:yyyy-MM-dd
	 * @return
	 */
	public static String getCurrDate(){
		return new SimpleDateFormat(DATE_YMD).format(new Date());
	}
	
	/**
	 * ��ǰʱ��:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrDateTime(){
		return new SimpleDateFormat(DATETIME_YMDHMS).format(new Date());
	}
	
	/**
	 * ��ǰʱ��:HH:mm:ss
	 * @return
	 */
	public static String getCurrTime(){
		return new SimpleDateFormat(DATETIME_HMS).format(new Date());
	}
	
	public static String getDateTimeStr(Date date){
		return new SimpleDateFormat(DATETIME_YMDHMS).format(date);
	}
	
	public static String getDateStr(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String getDateStr(long date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String getDateText(Context context,long date){
		
		Calendar recordCal = GregorianCalendar.getInstance();
		recordCal.setTimeInMillis(date);
		
		Calendar currentCal = GregorianCalendar.getInstance();
		
		long current = currentCal.getTimeInMillis();
		long sub = current - date;
		
		if(sub < MINUTE_DISTANCE){
			return context.getString(R.string.just_now);
		}else if(sub < HOUR_DISTANCE){
			return context.getString(R.string.before_minute,(sub / (1000*60)));//��ʾ:1����ǰ - 60 ����ǰ
		}
		if(recordCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)){
			
			if(recordCal.get(Calendar.DAY_OF_YEAR) == currentCal.get(Calendar.DAY_OF_YEAR)){//����
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return context.getString(R.string.today)+SDF_DATE_HM.format(recordCal.getTime());//��ʾ�� HH:mm
			}else if(recordCal.get(Calendar.DAY_OF_YEAR) == (currentCal.get(Calendar.DAY_OF_YEAR) -1) ){
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return context.getString(R.string.yesterday) + SDF_DATE_HM.format(recordCal.getTime());//��ʾ������ HH:mm
			}else{
				SDF_DATE_MD.setTimeZone(TimeZone.getDefault());
				return SDF_DATE_MD.format(recordCal.getTime());//��ʾ��MM-dd
			}
			
		}else{
			SDF_DATE_YMD.setTimeZone(TimeZone.getDefault());
			return SDF_DATE_YMD.format(recordCal.getTime());//��ʾ��yyyy-MM-dd
		}
		
	}
	
	private static final String[] WEEK = {
		"","����","����һ","���ڶ�","������","������","������","����"
	};
	
	public static String getDateAndWeekText(long date){
		Calendar currentCal = GregorianCalendar.getInstance();
		currentCal.setTimeInMillis(date);
		return SDF_DATE_YMD.format(currentCal.getTime())+WEEK[currentCal.get(Calendar.DAY_OF_WEEK)];
	}
	
	public static boolean isTheSameDay(long d1, long d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTimeInMillis(d1);
		c2.setTimeInMillis(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2
						.get(Calendar.DAY_OF_MONTH));
	}

	
	public static Date parseDate(String dateStr,String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDateText(long date){
		
		Calendar recordCal = GregorianCalendar.getInstance();
		recordCal.setTimeInMillis(date);
		
		Calendar currentCal = GregorianCalendar.getInstance();
		
		long current = currentCal.getTimeInMillis();
		long sub = current - date;
		
		if(sub < MINUTE_DISTANCE){
			return "�ո�";
		}else if(sub < HOUR_DISTANCE){
			return (sub / (1000*60)) +" ����ǰ";//��ʾ:1����ǰ - 60 ����ǰ
		}
		if(recordCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)){
			
			if(recordCal.get(Calendar.DAY_OF_YEAR) == currentCal.get(Calendar.DAY_OF_YEAR)){//����
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return "���� "+SDF_DATE_HM.format(recordCal.getTime());//��ʾ�� HH:mm
			}else if(recordCal.get(Calendar.DAY_OF_YEAR) == (currentCal.get(Calendar.DAY_OF_YEAR) -1) ){
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return "���� " + SDF_DATE_HM.format(recordCal.getTime());//��ʾ������ HH:mm
			}else{
				SDF_DATE_MD.setTimeZone(TimeZone.getDefault());
				return SDF_DATE_MD.format(recordCal.getTime());//��ʾ��MM-dd
			}
			
		}else{
			SDF_DATE_YMD.setTimeZone(TimeZone.getDefault());
			return SDF_DATE_YMD.format(recordCal.getTime());//��ʾ��yyyy-MM-dd
		}
		
	}
	
	public static String getDateText(long start,long end){
		Calendar startCal = GregorianCalendar.getInstance();
		Calendar endCal = GregorianCalendar.getInstance();
		
		startCal.setTimeInMillis(start);
		endCal.setTimeInMillis(end);
		SDF_DATE_MY.setTimeZone(TimeZone.getDefault());
		SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
		
		if(startCal.get(Calendar.DAY_OF_YEAR) == endCal.get(Calendar.DAY_OF_YEAR)){//����
			StringBuilder sb = new StringBuilder();
			sb.append(SDF_DATE_MY.format(startCal.getTime()));
			sb.append(" ");
			sb.append(SDF_DATE_HM.format(startCal.getTime()));
			sb.append(" ~ ");
			sb.append(SDF_DATE_HM.format(endCal.getTime()));
			return sb.toString();
		}else{
			
			StringBuilder sb = new StringBuilder();
			sb.append(SDF_DATE_MY.format(startCal.getTime()));
			sb.append(" ");
			sb.append(SDF_DATE_HM.format(startCal.getTime()));
			sb.append(" ~ ");
			sb.append(SDF_DATE_MY.format(startCal.getTime()));
			sb.append(" ");
			sb.append(SDF_DATE_HM.format(endCal.getTime()));
			return sb.toString();
		}
	}
}
