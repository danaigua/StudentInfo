package com.qiuzhisystem.action;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.ClassDao;
import com.qiuzhisystem.dao.DataDicDao;
import com.qiuzhisystem.dao.GradeDao;
import com.qiuzhisystem.dao.StudentDao;
import com.qiuzhisystem.model.DataDic;
import com.qiuzhisystem.model.Grade;
import com.qiuzhisystem.model.PageBean;
import com.qiuzhisystem.model.Student;
import com.qiuzhisystem.utils.DateUtil;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.NavUtil;
import com.qiuzhisystem.utils.PageUtil;
import com.qiuzhisystem.utils.PropertiesUtil;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONObject;

import com.qiuzhisystem.model.Class;

public class StudentAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	private DbUtil dbUtil = new DbUtil();
	private StudentDao studentDao = new StudentDao();
	private DataDicDao dataDicDao = new DataDicDao();
	private GradeDao gradeDao = new GradeDao();
	private ClassDao classDao = new ClassDao();
	private Student student;
	private List<Student> studentList = new ArrayList<Student>();
	private String mainPage;
	private String navCode;
	private String studentId;
	private List<DataDic> s_sexDataDicList;
	private List<DataDic> s_nationDataDicList;
	private List<Grade> s_gradeList;
	private List<Class> s_classList;
	private Student s_student;
	
	private String page;
	private int total;
	private String pageCode;
	
	private List<DataDic> zzmmDataDicList;
	private List<DataDic> nationDataDicList;
	private List<DataDic> sexDataDicList;
	private List<Class> classList;

	private String classId;
	
	private File stuPic;
	private String stuPicFileName;
	
	public File getStuPic() {
		return stuPic;
	}
	public void setStuPic(File stuPic) {
		this.stuPic = stuPic;
	}
	public String getStuPicFileName() {
		return stuPicFileName;
	}
	public void setStuPicFileName(String stuPicFileName) {
		this.stuPicFileName = stuPicFileName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public List<DataDic> getZzmmDataDicList() {
		return zzmmDataDicList;
	}
	public void setZzmmDataDicList(List<DataDic> zzmmDataDicList) {
		this.zzmmDataDicList = zzmmDataDicList;
	}
	public List<DataDic> getNationDataDicList() {
		return nationDataDicList;
	}
	public void setNationDataDicList(List<DataDic> nationDataDicList) {
		this.nationDataDicList = nationDataDicList;
	}
	public List<DataDic> getSexDataDicList() {
		return sexDataDicList;
	}
	public void setSexDataDicList(List<DataDic> sexDataDicList) {
		this.sexDataDicList = sexDataDicList;
	}
	public List<Class> getClassList() {
		return classList;
	}
	public void setClassList(List<Class> classList) {
		this.classList = classList;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public Student getS_student() {
		return s_student;
	}
	public void setS_student(Student s_student) {
		this.s_student = s_student;
	}
	public List<DataDic> getS_sexDataDicList() {
		return s_sexDataDicList;
	}
	public void setS_sexDataDicList(List<DataDic> s_sexDataDicList) {
		this.s_sexDataDicList = s_sexDataDicList;
	}
	public List<DataDic> getS_nationDataDicList() {
		return s_nationDataDicList;
	}
	public void setS_nationDataDicList(List<DataDic> s_nationDataDicList) {
		this.s_nationDataDicList = s_nationDataDicList;
	}
	public List<Grade> getS_gradeList() {
		return s_gradeList;
	}
	public void setS_gradeList(List<Grade> s_gradeList) {
		this.s_gradeList = s_gradeList;
	}
	public List<Class> getS_classList() {
		return s_classList;
	}
	public void setS_classList(List<Class> s_classList) {
		this.s_classList = s_classList;
	}
	public StudentDao getStudentDao() {
		return studentDao;
	}
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
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
	 * 通过分页并且模糊查询来列出学生信息
	 * @return
	 */
	public String list() {
		//把分页信息存到session中
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		if(s_student!=null) {
			session.setAttribute("s_student", s_student);
		}else {
			Object o = session.getAttribute("s_student");
			if(o != null) {
				s_student = (Student)o;
			}else {
				s_student = new Student();
			}
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			total = studentDao.studentCount(con, s_student);
			studentList = studentDao.studentList(con, s_student, pageBean);
			navCode = NavUtil.getNavgation("学生信息管理", "学生维护");
			pageCode = PageUtil.genPagation(request.getContextPath() + "/studentList", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			DataDic s_dataDic = new DataDic();
			s_dataDic.setDdTypeName("性别");
			s_sexDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			
			s_dataDic.setDdTypeName("民族");
			s_nationDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			
			s_gradeList = gradeDao.gradeList(con);
			//解决二级联动查询之后，二级联动消失的问题
			if(s_student != null && s_student.getGradeId()!=-1) {
				Class s_class = new Class();
				s_class.setGradeId(s_student.getGradeId());
				s_classList = classDao.classList(con, s_class);
			}
			mainPage = "student/studentList.jsp";
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
	 * 删除学生信息
	 * @return
	 */
	public String delete() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject resultJson = new JSONObject();
			int i = studentDao.studentDelete(con, studentId);
			if (i > 0) {
				resultJson.put("success", true);
			}else {
				resultJson.put("error", "删除失败，请稍后重试");
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
	 * 展示学生信息
	 * @return
	 */
	public String show(){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			student=studentDao.getStudentById(con, studentId);
			navCode=NavUtil.getNavgation("学生信息管理", "学生详细信息");
			mainPage="student/studentShow.jsp";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 学生信息预保存
	 * @return
	 */
	public String preSave(){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(studentId)){
				student=studentDao.getStudentById(con, studentId);
			}
			DataDic s_dataDic=new DataDic();
			s_dataDic.setDdTypeName("性别");
			sexDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			
			s_dataDic.setDdTypeName("民族");
			nationDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			
			s_dataDic.setDdTypeName("政治面貌");
			zzmmDataDicList = dataDicDao.dataDicList(con, s_dataDic, null);
			
			classList = classDao.classList(con, null);
			if(StringUtil.isNotEmpty(studentId)){
				navCode=NavUtil.getNavgation("学生信息管理", "学生修改");				
			}else{
				navCode=NavUtil.getNavgation("学生信息管理", "学生添加");				
			}
			mainPage="student/studentSave.jsp";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 学生信息保存
	 * @return
	 */
	public String save(){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			//对于图片的处理
			if(stuPic!=null){
				String imageName=DateUtil.getCurrentDateStr();
				String realPath=ServletActionContext.getServletContext().getRealPath("/userImage");
				String imageFile=imageName+"."+stuPicFileName.split("\\.")[1];
				System.out.println(imageFile);
				File saveFile=new File(realPath,imageFile);
				FileUtils.copyFile(stuPic, saveFile);
				student.setStuPic(imageFile);
			}else if(StringUtil.isEmpty(student.getStuPic())){
				student.setStuPic("");
			}
			if(StringUtil.isNotEmpty(studentId)){
				student.setStudentId(studentId);
				studentDao.studentUpdate(con, student);
			}else{
				studentDao.studentAdd(con, student);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "save";
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
