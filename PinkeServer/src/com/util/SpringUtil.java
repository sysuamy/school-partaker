package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"/spring-servlet.xml");

	public static ApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(String beanName, Class<T> class_1) {
		T t = null;
		t = context.getBean(beanName, class_1);
		return t;
	}

	public static boolean isNotNull(String str) {
		if (str != null && !str.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
