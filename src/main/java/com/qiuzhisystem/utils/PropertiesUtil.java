package com.qiuzhisystem.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getValue(String key) throws IOException {
		//通过key取value
		Properties prop = new Properties();
		InputStream is = new PropertiesUtil().getClass().getResourceAsStream("/studentInfoManage.properties");
		prop.load(is);
		return prop.getProperty(key);
	}
}
