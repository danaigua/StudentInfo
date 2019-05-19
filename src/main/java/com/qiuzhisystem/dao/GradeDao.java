package com.qiuzhisystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.qiuzhisystem.model.Grade;

public class GradeDao {
	/**
	 * 查出所有的班级
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Grade> gradeList(Connection conn) throws Exception{
		List<Grade> gradeList = new ArrayList<Grade>();
		String sql = "select * from t_grade";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Grade grade = new Grade();
			grade.setGradeId(rs.getInt("gradeId"));
			grade.setGradeName(rs.getString("gradeName"));
			grade.setGradeDesc(rs.getString("gradeDesc"));
			gradeList.add(grade);
		}
		return gradeList;
	}
	/**
	 * 删除班级
	 * @param conn
	 * @param gradeId
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection conn, String gradeId) throws Exception{
		String sql = "delete from t_grade where gradeId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(gradeId));
		return pstmt.executeUpdate();
	}
	/**
	 * 更新班级数据
	 * @param conn
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeUpdate(Connection conn, Grade grade)throws Exception{
		String sql = "update t_grade set gradeName = ? , gradeDesc = ? where gradeId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getGradeId());
		return pstmt.executeUpdate();
	}
	/**
	 * 通过id得到一个班级的信息，可以进行编辑操作
	 * @param conn
	 * @param gradeId
	 * @return
	 * @throws Exception
	 */
	public Grade getGradeByGradeId(Connection conn, String gradeId) throws Exception{
		Grade grade = new Grade();
		String sql="select * from t_grade where gradeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			grade.setGradeId(rs.getInt("gradeId"));
			grade.setGradeName(rs.getString("gradeName"));
			grade.setGradeDesc(rs.getString("gradeDesc"));
		}
		return grade;
	}
	/**
	 * 添加班级
	 * @param conn
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeAdd(Connection conn, Grade grade) throws Exception{
		String sql = "insert into t_grade values (null, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
}
