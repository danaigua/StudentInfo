package com.qiuzhisystem.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.UserDao;
import com.qiuzhisystem.model.User;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.StringUtil;

public class UserAction extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private User user;
	
	private String error;
	
	private String imageCode;
	
	
	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	private DbUtil dbUtil = new DbUtil();
	private UserDao userDao = new UserDao();
	public String login()throws Exception{
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			error = "用户名密码为空";
			return ERROR;
		}
		if(StringUtil.isEmpty(imageCode)) {
			error = "验证码为空";
			return ERROR;
		}
		if(!imageCode.equals(session.getAttribute("sRand"))) {
			error = "验证码错误";
			return ERROR;
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			User currentUser = userDao.login(con, user);
			if(currentUser==null) {
				error="用户名或密码错误！";
				return ERROR;
			}else {
				session.setAttribute("currentUser", currentUser);
				return SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbUtil.closeCon(con);
		}
		
		return SUCCESS;
		
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
