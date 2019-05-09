package com.qiuzhisystem.utils;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
//	public static void main(String[] args) {
//		DbUtil dbUtil = new DbUtil();
//		try {
//			dbUtil.getCon();
//			System.out.println("链接成功");
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("链接失败");
//		}
//	}
	public Connection getCon() throws Exception {
		Class.forName(PropertiesUtil.getValue("jdbc.driverClassName"));
		Connection conn = DriverManager.getConnection(PropertiesUtil.getValue("jdbc.url"),
													  PropertiesUtil.getValue("jdbc.username"),
													  PropertiesUtil.getValue("jdbc.password"));
		return conn;
	}
	public void closeCon(Connection con)throws Exception{
		if(con!=null) {
			con.close();
		}
	}
}
