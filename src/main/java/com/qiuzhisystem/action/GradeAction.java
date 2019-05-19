package com.qiuzhisystem.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.ClassDao;
import com.qiuzhisystem.dao.GradeDao;
import com.qiuzhisystem.model.Grade;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.NavUtil;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONObject;

public class GradeAction extends ActionSupport{

	private DbUtil dbUtil = new DbUtil();
	private GradeDao gradeDao = new GradeDao();
	private List<Grade> gradeList = new ArrayList<Grade>();
	private String mainPage;
	private String navCode;
	
	private String gradeId;
	private Grade grade;
	private ClassDao classDao = new ClassDao();
	
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public List<Grade> getGradeList() {
		return gradeList;
	}
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
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
	 * 列出所有的班级，没有分页
	 * @return
	 */
	public String list() {
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			gradeList = gradeDao.gradeList(conn);
			navCode = NavUtil.getNavgation("系统管理", "年级维护");
			mainPage = "grade/gradeList.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 保存操作
	 * @return
	 */
	public String save() {
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			if(StringUtil.isEmpty(gradeId)) {
				//如果gradeId为空说明是添加，不是空说明是编辑
				gradeDao.gradeAdd(conn, grade);
			}else {
				grade.setGradeId(Integer.parseInt(gradeId));
				gradeDao.gradeUpdate(conn, grade);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "save";
	}
	/**
	 * 预保存操作
	 * @return
	 */
	public String preSave() {
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			if(StringUtil.isEmpty(gradeId)) {
				//如果gradeId为空的话就执行添加操作
				navCode = NavUtil.getNavgation("系统管理", "年纪信息添加");
			}else {
				grade = gradeDao.getGradeByGradeId(conn, gradeId);
				navCode = NavUtil.getNavgation("系统管理", "年纪信息修改");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mainPage="grade/gradeSave.jsp";
		return SUCCESS;
	}
	/**
	 * 删除操作
	 * @return
	 */
	public String delete(){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject resultJson=new JSONObject();
			boolean exist=classDao.existClass(con, gradeId);
			if(exist){
				resultJson.put("error", "年级下面有班级，不能删除！");
			}else{
				gradeDao.gradeDelete(con, gradeId);
				resultJson.put("success", true);
			}
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
