package com.qiuzhisystem.utils;

import java.util.UUID;

public class UUIDUtil {
	/**
	 * 生成一个码，据说几百万年不重复
	 * @return
	 */
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtil.getUUID());
	}
}
