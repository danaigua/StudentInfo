package com.qiuzhisystem.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.DataDicDao;
import com.qiuzhisystem.dao.DataDicTypeDao;
import com.qiuzhisystem.model.DataDic;
import com.qiuzhisystem.model.DataDicType;
import com.qiuzhisystem.model.PageBean;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.NavUtil;
import com.qiuzhisystem.utils.PageUtil;
import com.qiuzhisystem.utils.PropertiesUtil;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONObject;

public class DataDicAction extends ActionSupport implements ServletRequestAware{
private static final long serialVersionUID = 1L;
	
	private DbUtil dbUtil=new DbUtil();
	private DataDicDao dataDicDao=new DataDicDao();
	private List<DataDic> dataDicList=new ArrayList<DataDic>();
	
	private String mainPage; 
	private String navCode;
	
	private String s_ddTypeName;
	
	private String page;
	private int total;
	private String pageCode;
	
	private String ddId;
	
	
	//预保存需要用到的变量
	private List<DataDicType> dataDicTypeList = new ArrayList<DataDicType>();
	private DataDicTypeDao dataDicTypeDao = new DataDicTypeDao();
	
	private DataDic dataDic;
	
	
	
	
	public List<DataDicType> getDataDicTypeList() {
		return dataDicTypeList;
	}

	public void setDataDicTypeList(List<DataDicType> dataDicTypeList) {
		this.dataDicTypeList = dataDicTypeList;
	}

	public DataDic getDataDic() {
		return dataDic;
	}

	public void setDataDic(DataDic dataDic) {
		this.dataDic = dataDic;
	}

	public String getDdId() {
		return ddId;
	}

	public void setDdId(String ddId) {
		this.ddId = ddId;
	}
	private HttpServletRequest request;
	
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

	public List<DataDic> getDataDicList() {
		return dataDicList;
	}

	public void setDataDicList(List<DataDic> dataDicList) {
		this.dataDicList = dataDicList;
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

	public String getS_ddTypeName() {
		return s_ddTypeName;
	}

	public void setS_ddTypeName(String s_ddTypeName) {
		this.s_ddTypeName = s_ddTypeName;
	}
/**
 * 分页获取数据字典数量
 * @return
 */
	public String list(){
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		Connection con=null;
		try{
			HttpSession session = request.getSession();
			/**
			 * 把s_ddTypeName存到session中，这样子就避免了刷新之后s_ddTypeName没有带过去
			 */
			DataDic s_dataDic=new DataDic();
			if(s_ddTypeName!=null){
				s_dataDic.setDdTypeName(s_ddTypeName);
				session.setAttribute("s_ddTypeName", s_ddTypeName);
			}else {
				Object o = session.getAttribute("s_ddTypeName");
				if(o != null) {
					s_ddTypeName = (String)session.getAttribute("s_ddTypeName");
					s_dataDic.setDdTypeName(s_ddTypeName);
				}
			}
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			con=dbUtil.getCon();
			dataDicList=dataDicDao.dataDicList(con,s_dataDic,pageBean);
			total = dataDicDao.dataDicCount(con, s_dataDic);
			pageCode = PageUtil.genPagation(request.getContextPath() + "/dataDicList", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			navCode=NavUtil.getNavgation("系统管理", "数据字典维护");
			mainPage="dataDic/dataDicList.jsp";
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
	 * 删除的action
	 * @return
	 */
	public String delete(){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject resultJson=new JSONObject();
			dataDicDao.dataDicDelete(con, ddId);
			resultJson.put("success", true);
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
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	/**
	 * 预保存
	 * @return
	 */
	public String preSave() {
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			dataDicTypeList = dataDicTypeDao.dataDicTypeList(conn);
			//不为空的话表明是修改的，需要根据ddId来获取实体
			if(StringUtil.isEmpty(ddId)) {
				navCode = NavUtil.getNavgation("系统管理", "数据字典添加");
			}else {
				dataDic = dataDicDao.getDataDicById(conn, ddId);
				navCode = NavUtil.getNavgation("系统管理", "数据字典修改");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mainPage="dataDic/dataDicSave.jsp";
		return SUCCESS;
	}
	/**
	 * 保存操作
	 * @return
	 */
	public String save() {
		Connection con = null;
		try{
			con = dbUtil.getCon();
			if(StringUtil.isEmpty(ddId)){
				dataDicDao.dataDicAdd(con, dataDic);
			}else{
				dataDic.setDdId(Integer.parseInt(ddId));
				dataDicDao.dataDicUpdate(con, dataDic);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.getCon();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "save";
	}
}
