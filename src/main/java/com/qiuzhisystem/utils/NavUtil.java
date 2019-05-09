package com.qiuzhisystem.utils;

import java.io.IOException;

public class NavUtil {
	public static String getNavgation(String mainMenu,String subMenu) throws Exception {
		StringBuffer navCode = new StringBuffer();
		navCode.append("当前位置：&nbsp;&nbsp");
		navCode.append("<a href='"+PropertiesUtil.getValue("projectName")+"/main.jsp'>主页</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		navCode.append("<a href='#'>"+mainMenu+"</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		navCode.append("<a href='#'>"+subMenu+"</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		return navCode.toString();
	}
}
