package com.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	
	/**
	 * 将其它类型的数据转化为json字符串
	 * @param returnObj 需要转化的其他类型数据
	 * @return 字符串
	 */
	public static String jsonUtil(Object returnObj){
		StringWriter stringWriter = new StringWriter();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = null;
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					stringWriter);
			objectMapper.writeValue(jsonGenerator, returnObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonStr = stringWriter.toString();
		return jsonStr;
	}
}
