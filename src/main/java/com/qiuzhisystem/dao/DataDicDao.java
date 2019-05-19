package com.qiuzhisystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.qiuzhisystem.model.DataDic;
import com.qiuzhisystem.model.PageBean;
import com.qiuzhisystem.utils.StringUtil;
/**
 * 数据字典dao层
 * @author 12952
 *
 */
public class DataDicDao {
	/**
	 * 判断数据字典是否有值
	 * @param conn
	 * @param ddTypeId
	 * @return
	 * @throws Exception
	 */
	public boolean existDataDicByTypeId(Connection conn, String ddTypeId)throws Exception{
		String sql = "select * from t_dataDic where ddTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, ddTypeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 根据分页的情况以及关键词模糊查询
	 * @param conn
	 * @param s_dataDic
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<DataDic> dataDicList(Connection conn, DataDic s_dataDic, PageBean pageBean) throws Exception{
		List<DataDic> dataDicList=new ArrayList<DataDic>();
		StringBuffer sb = new StringBuffer("select * from t_dataDic t1,t_dataDicType t2 where t1.ddTypeId=t2.ddTypeId");
		if(StringUtil.isNotEmpty(s_dataDic.getDdTypeName())){
			sb.append(" and t2.ddTypeName like '%"+s_dataDic.getDdTypeName()+"%'");
		}
		if(pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}
		PreparedStatement pstmt=conn.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			DataDic dataDic=new DataDic();
			dataDic.setDdId(rs.getInt("ddId"));
			dataDic.setDdTypeId(rs.getInt("ddTypeId"));
			dataDic.setDdTypeName(rs.getString("ddTypeName"));
			dataDic.setDdValue(rs.getString("ddValue"));
			dataDic.setDdDesc(rs.getString("ddDesc"));
			dataDicList.add(dataDic);
		}
		return dataDicList;
	}
	/**
	 * 查看查询到的总数
	 * @param conn
	 * @param s_dataDic 查询的关键词
	 * @return
	 * @throws Exception
	 */
	public int dataDicCount(Connection conn, DataDic s_dataDic) throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_dataDic t1,t_dataDicType t2 where t1.ddTypeId=t2.ddTypeId");
		if(StringUtil.isNotEmpty(s_dataDic.getDdTypeName())){
			sb.append(" and t2.ddTypeName like '%"+s_dataDic.getDdTypeName()+"%'");
		}
		PreparedStatement pstmt=conn.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	/**
	 * 删除数据字典
	 * @param con
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	public int dataDicDelete(Connection con,String Id)throws Exception{
		String sql="delete from t_dataDic where ddId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,Id);
		return pstmt.executeUpdate();
	}
	/**
	 * 添加一个数据字典
	 * @param conn
	 * @param dataDic
	 * @return
	 * @throws Exception
	 */
	public int dataDicAdd(Connection conn, DataDic dataDic) throws Exception{
		String sql  = "insert into t_dataDic values (null, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, dataDic.getDdTypeId());
		pstmt.setString(2, dataDic.getDdValue());
		pstmt.setString(3, dataDic.getDdDesc());
		return pstmt.executeUpdate();
	}
	/**
	 * 编辑数据字典
	 * @param conn
	 * @param dataDic
	 * @return
	 * @throws Exception
	 */
	public int dataDicUpdate(Connection conn, DataDic dataDic) throws Exception{
		String sql = "update t_dataDic set ddTypeId = ?, ddValue = ?, ddDesc = ? where ddId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, dataDic.getDdTypeId());
		pstmt.setString(2, dataDic.getDdValue());
		pstmt.setString(3, dataDic.getDdDesc());
		pstmt.setInt(4, dataDic.getDdId());
		return pstmt.executeUpdate();
	}
	/**
	 * 通过id查找数据字典
	 * @param conn
	 * @param ddId
	 * @return
	 * @throws Exception
	 */
	public DataDic getDataDicById(Connection conn, String ddId) throws Exception{
		String sql = "select * from t_datadic t1, t_datadictype t2 where t1.ddTypeId = t2.ddTypeId and ddId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, ddId);
		ResultSet rs = pstmt.executeQuery();
		DataDic dataDic = new DataDic();
		if(rs.next()) {
			dataDic.setDdId(rs.getInt("ddId"));
			dataDic.setDdTypeId(rs.getInt("ddTypeId"));
			dataDic.setDdTypeName(rs.getString("ddTypeName"));
			dataDic.setDdValue(rs.getString("ddValue"));
			dataDic.setDdDesc(rs.getString("ddDesc"));
		}
		return dataDic;
	}
}
