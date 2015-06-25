package com.zx.pinke.util;

public class LogFactory {

	public static Log getLog(Class<?> cls){
		Log logger = new Log(cls);
		return logger;
	}
}
