<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	function gradeDelete(gradeId){
		if(confirm("确定要删除这条数据字典吗?")){
			$.post("gradeDelete",{gradeId:gradeId},
					function(result){
						alert("删除成功！");
						window.location.href="${pageContext.request.contextPath}/gradeList";
					}
			);
		}
	}
</script>
<div class="data_list">
	<div class="search_content">
		<button class="btn btn-mini btn-primary" type="button" style="float: right;margin-bottom: 5px;" onclick="javascript:window.location='gradePreSave'">添加年级</button>
	</div>
	<div class="data_content">
		<table class="table table-bordered table-hover">
			<tr>
				<th>序号</th>
				<th>年纪名称</th>
				<th>年纪备注</th>
				<th>操作</th>
			</tr>
			<c:forEach var="grade" items="${gradeList}" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${grade.gradeName }</td>
					<td>${grade.gradeDesc }</td>
					<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='gradePreSave?gradeId=${grade.gradeId}'">修改</button>&nbsp;&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="gradeDelete(${grade.gradeId})">删除</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pagination pagination-centered">
		<ul>
			${pageCode}
		</ul>
	</div>
</div>
