package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author 小败
 *
 */
public class DateUtil {
	/**
	 * 得到时间戳
	 * @return 字符串
	 */
	public static String getDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String strTime = simpleDate.format(date);
		return strTime;
	}
	/**
	 * 得到格式化之后的时间字符串
	 * @return 字符串
	 */
	public static String getFormatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
		String strTime = simpleDate.format(date);
		return strTime;
	}
	/**
	 * 得到更具体的时间字符串
	 * @return 字符串
	 */
	public static String getMoreFormatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
		String strTime = simpleDate.format(date);
		return strTime;
	}
}
