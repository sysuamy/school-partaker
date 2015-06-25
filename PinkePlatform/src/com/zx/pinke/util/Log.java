package com.zx.pinke.util;

public class Log {

	private static final boolean V = true;
	private Class<?> cls;
	Log(Class<?> cls){
		this.cls = cls;
	}
	
	public void debug(Object msg){
		android.util.Log.d(cls.getSimpleName(), msg == null ? "null" : msg.toString());
		if(V){
			System.out.println(msg == null ? "null" : msg.toString());
		}
	}
	
	public void error(Object msg){
		android.util.Log.e(cls.getSimpleName(), msg == null ? "null" : msg.toString());
		if(V){
			System.out.println(msg == null ? "null" : msg.toString());
		}
	}
	
	public void info(Object msg){
		android.util.Log.i(cls.getSimpleName(), msg == null ? "null" : msg.toString());
		if(V){
			System.out.println(msg == null ? "null" : msg.toString());
		}
	}
	
	public void warn(Object msg){
		android.util.Log.w(cls.getSimpleName(), msg == null ? "null" : msg.toString());
		if(V){
			System.out.println(msg == null ? "null" : msg.toString());
		}
	}
}
