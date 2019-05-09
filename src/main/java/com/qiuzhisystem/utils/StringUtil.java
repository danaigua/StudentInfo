package com.qiuzhisystem.utils;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if("".equals(str) || str == null) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 判断字符串是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if(!"".equals(str)&&str != null) {
			return true;
		}else {
			return false;
		}
	}
}
