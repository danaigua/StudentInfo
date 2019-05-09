package com.qiuzhisystem.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.qiuzhisystem.dao.DataDicDao;
import com.qiuzhisystem.dao.DataDicTypeDao;
import com.qiuzhisystem.model.DataDicType;
import com.qiuzhisystem.utils.DbUtil;
import com.qiuzhisystem.utils.NavUtil;
import com.qiuzhisystem.utils.ResponseUtil;
import com.qiuzhisystem.utils.StringUtil;

import net.sf.json.JSONObject;

public class DataDicTypeAction extends ActionSupport{
	private DbUtil dbUtil = new DbUtil();
	private DataDicTypeDao dataDicTypeDao = new DataDicTypeDao();
	private DataDicDao dataDicDao = new DataDicDao();
	private List<DataDicType> dataDicTypeList = new ArrayList<DataDicType>();
	private String mainPage;
	private String navCode;
	
	private String ddTypeId;
	private DataDicType dataDicType;
	
	public DataDicType getDataDicType() {
		return dataDicType;
	}
	public void setDataDicType(DataDicType dataDicType) {
		this.dataDicType = dataDicType;
	}
	public DataDicTypeDao getDataDicTypeDao() {
		return dataDicTypeDao;
	}
	public void setDataDicTypeDao(DataDicTypeDao dataDicTypeDao) {
		this.dataDicTypeDao = dataDicTypeDao;
	}
	public String getDdTypeId() {
		return ddTypeId;
	}
	public void setDdTypeId(String ddTypeId) {
		this.ddTypeId = ddTypeId;
	}
	public List<DataDicType> getDataDicTypeList() {
		return dataDicTypeList;
	}
	public void setDataDicTypeList(List<DataDicType> dataDicTypeList) {
		this.dataDicTypeList = dataDicTypeList;
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
	 * 列出所有的数据类型
	 * @return
	 */
	public String list() {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			dataDicTypeList = dataDicTypeDao.dataDicTypeList(con);
			navCode = NavUtil.getNavgation("系统管理", "数据字典类别维护");
			mainPage="dataDicType/dataDicTypeList.jsp";
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	//预处理
	public String preSave() throws Exception {
		if(StringUtil.isNotEmpty(ddTypeId)) {
			Connection conn = null;
			try {
				conn = dbUtil.getCon();
				dataDicType = dataDicTypeDao.getDataDicTypeById(conn, ddTypeId);
				navCode = NavUtil.getNavgation("系统管理", "数据字典类别修改");
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					dbUtil.closeCon(conn);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			navCode = NavUtil.getNavgation("系统管理", "数据字典类别添加");
		}
		mainPage="dataDicType/dataDicTypeSave.jsp";
		return SUCCESS;
	}
	/**
	 * 添加或者保存数据字典
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		Connection conn = null;
		try {
			conn = dbUtil.getCon();
			if(StringUtil.isNotEmpty(ddTypeId)) {
				//修改
				dataDicType.setDdTypeId(Integer.parseInt(ddTypeId));
				dataDicTypeDao.dataDicTypeUpdate(conn, dataDicType);
			}else {
				//添加
				dataDicTypeDao.dataDicTypeAdd(conn, dataDicType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbUtil.closeCon(conn);
		}
		return "typeSave";
	}
	/**
	 * 删除数据字典类别
	 * @return
	 */
	public String delete() {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject resultJson=new JSONObject();
			boolean exist=dataDicDao.existDataDicByTypeId(con, ddTypeId);
			if(exist){
				resultJson.put("error", "数据字典类别下面有数据，不能删除！");
			}else{
				dataDicTypeDao.dataDicTypeDelete(con, ddTypeId);
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
