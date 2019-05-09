package com.qiuzhisystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qiuzhisystem.model.DataDicType;


public class DataDicTypeDao {
	/**
	 * 查出数据字典类别
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<DataDicType> dataDicTypeList(Connection conn) throws Exception{
		List<DataDicType> dataDicTypeList = new ArrayList<DataDicType>();
		String sql = "select * from t_dataDicType";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DataDicType dataDicType = new DataDicType();
			dataDicType.setDdTypeId(rs.getInt("ddTypeId"));
			dataDicType.setDdTypeName(rs.getString("ddTypeName"));
			dataDicType.setDdTypeDesc(rs.getString("ddTypeDesc"));
			dataDicTypeList.add(dataDicType);
		}
		return dataDicTypeList;
	}
	/**
	 * 数据字典类别添加
	 * @param conn
	 * @param dataDicType
	 * @return
	 * @throws Exception
	 */
	public int dataDicTypeAdd(Connection conn,DataDicType dataDicType) throws Exception{
		String sql = "insert into t_dataDicType value(null,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dataDicType.getDdTypeName());
		pstmt.setString(2, dataDicType.getDdTypeDesc());
		return pstmt.executeUpdate();
	}
	/**
	 * 通过id查出数据字典
	 * @param conn
	 * @param ddTypeId
	 * @return
	 * @throws Exception
	 */
	public DataDicType getDataDicTypeById(Connection conn, String ddTypeId) throws Exception {
		String sql = "select * from t_dataDicType where ddTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, ddTypeId);
		ResultSet rs = pstmt.executeQuery();
		DataDicType dataDicType = new DataDicType();
		while(rs.next()) {
			dataDicType.setDdTypeId(rs.getInt("ddTypeId"));
			dataDicType.setDdTypeName(rs.getString("ddTypeName"));
			dataDicType.setDdTypeDesc(rs.getString("ddTypeDesc"));
		}
		return dataDicType;
	}
	/**
	 * 数据字典类别更新
	 * @param conn
	 * @param dataDicType
	 * @return
	 * @throws Exception
	 */
	public int dataDicTypeUpdate(Connection conn,DataDicType dataDicType)throws Exception{
		String sql = "update t_dataDicType set ddTypeName=?,ddTypeDesc=? where ddTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dataDicType.getDdTypeName());
		pstmt.setString(2, dataDicType.getDdTypeDesc());
		pstmt.setInt(3, dataDicType.getDdTypeId());
		return pstmt.executeUpdate();
	}
	/**
	 * 数据字典类型删除
	 * @param conn
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public int dataDicTypeDelete(Connection conn,String typeId) throws Exception{
		String sql = "delete from t_dataDicType where ddTypeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, typeId);
		return pstmt.executeUpdate();
	}
}
