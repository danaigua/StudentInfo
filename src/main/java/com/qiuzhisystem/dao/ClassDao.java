package com.qiuzhisystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.qiuzhisystem.model.Class;
public class ClassDao {
	/**
	 * 在年级管理的时候如果年级下面有班级就不能删除
	 * @param con
	 * @param gradeId
	 * @return
	 * @throws Exception
	 */
	public boolean existClass(Connection con,String gradeId)throws Exception{
		String sql="select * from t_class where gradeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,gradeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 查出所有的班级，关联查询
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Class> classList(Connection con, Class s_class) throws Exception{
		List<Class> classList = new ArrayList<Class>();
		StringBuffer sb = new StringBuffer("select * from t_class t1, t_grade t2 where t1.gradeId = t2.gradeId");
		if(s_class!=null && s_class.getGradeId()!=-1){
			sb.append(" and t1.gradeId="+s_class.getGradeId());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Class c = new Class();
			c.setClassId(rs.getInt("classId"));
			c.setClassName(rs.getString("className"));
			c.setGradeId(rs.getInt("gradeId"));
			c.setGradeName(rs.getString("gradeName"));
			c.setClassDesc(rs.getString("classDesc"));
			classList.add(c);
		}
		return classList;
	}
	/**
	 * 删除一个班级
	 * @param con
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public int classDelete(Connection con, String classId) throws Exception{
		String sql = "delete from t_class where classId = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, classId);
		return pstmt.executeUpdate();
	}
	/**
	 * 添加一个班级
	 * @param con
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public int classAdd(Connection con, Class c) throws Exception{
		String sql = "insert into t_class values (null, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, c.getClassName());
		pstmt.setInt(2, c.getGradeId());
		pstmt.setString(3, c.getClassDesc());
		return pstmt.executeUpdate();
	}
	/**
	 * 修改一个班级的信息
	 * @param con
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public int classUpdate(Connection con, Class c) throws Exception{
		String sql = "update t_class set className = ?, gradeId = ?, classDesc = ? where classId = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, c.getClassName());
		pstmt.setInt(2, c.getGradeId());
		pstmt.setString(3, c.getClassDesc());
		pstmt.setInt(4, c.getClassId());
		return pstmt.executeUpdate();
	}
	/**
	 * 通过id获取实体
	 * @param con
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public Class getClassById(Connection con, String classId) throws Exception{
		String sql = "select * from t_class t1, t_grade t2 where t1.gradeId = t2.gradeId and t1.classId = ?";
		Class c = new Class();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, classId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			c.setClassId(rs.getInt("classId"));
			c.setClassName(rs.getString("className"));
			c.setClassDesc(rs.getString("classDesc"));
			c.setGradeId(rs.getInt("gradeId"));
			c.setGradeName(rs.getString("gradeName"));
		}
		return c;
	}
}
