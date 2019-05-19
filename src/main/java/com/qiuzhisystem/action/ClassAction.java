package com.qiuzhisystem.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.ClassDao;
import com.qiuzhisystem.dao.GradeDao;
import com.qiuzhisystem.dao.StudentDao;
import com.qiuzhisystem.model.Class;
import com.qiuzhisystem.model.Grade;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.NavUtil;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class c;
	private List<Class> classList = new ArrayList<Class>();
	private String mainPage;
	private String navCode;
	private DbUtil dbUtil = new DbUtil();
	
	private ClassDao classDao = new ClassDao();
	
	private List<Grade> gradeList = new ArrayList<Grade>();
	private GradeDao gradeDao = new GradeDao();
	
	private String classId;

	private String s_gradeId;
	
	private StudentDao studentDao;
	
	public String getS_gradeId() {
		return s_gradeId;
	}
	public void setS_gradeId(String s_gradeId) {
		this.s_gradeId = s_gradeId;
	}
	public List<Grade> getGradeList() {
		return gradeList;
	}
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public Class getC() {
		return c;
	}
	public void setC(Class c) {
		this.c = c;
	}

	public List<Class> getClassList() {
		return classList;
	}
	public void setClassList(List<Class> classList) {
		this.classList = classList;
	}
	public String getMainPage() {
		return mainPage;
	}
	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	
	public String getNavCode() {
		return navCode;
	}
	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}
	/**
	 * 列出所有的班级
	 * @return
	 */
	public String list() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			classList = classDao.classList(con, null);
			mainPage = "class/classList.jsp";
			navCode = NavUtil.getNavgation("班级信息管理", "班级维护");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 删除一个班级
	 * @return
	 */
	public String delete() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject resultJson = new JSONObject();
			classDao.classDelete(con, classId);
			boolean b = studentDao.existStudentByClassId(con, classId);
			if(b) {
				resultJson.put("error", "班级下面有学生，不能删除");
			}else {
				classDao.classDelete(con, classId);
				resultJson.put("success", true);
			}
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 预保存操作
	 * @return
	 */
	public String preSave() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			gradeList = gradeDao.gradeList(con);
			if(StringUtil.isNotEmpty(classId)) {
				c = classDao.getClassById(con, classId);
				navCode = NavUtil.getNavgation("班级管理", "班级修改");
			}else {
				navCode = NavUtil.getNavgation("班级管理", "班级添加");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mainPage = "class/classSave.jsp";
		return SUCCESS;
	}
	/**
	 * 保存操作
	 * @return
	 */
	public String save() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			if(StringUtil.isNotEmpty(classId)) {
				c.setClassId(Integer.parseInt(classId));
				classDao.classUpdate(con, c);
			}else {
				classDao.classAdd(con, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "save";
	}
	/**
	 * 通过年级id二级联动查出年级下面的班级
	 * @return
	 */
	public String getClassByGradeId() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			Class s_class = new Class();
			s_class.setGradeId(Integer.parseInt(s_gradeId));
			List<Class> classList = classDao.classList(con, s_class);
			JSONArray jsonArray = JSONArray.fromObject(classList);
			ResponseUtil.write(jsonArray, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
}
