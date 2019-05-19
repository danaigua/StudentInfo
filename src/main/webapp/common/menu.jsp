<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	function logOut(){
		if(confirm("您确定要退出系统吗？")){
			window.location.href="userlogOut";
		}
	}
</script>
<div class="row-fluid">
	<div class="span12">
		<div class="navbar">
		  <div class="navbar-inner">
		    <a class="brand" href="main.jsp">首页</a>
		    <ul class="nav">
		    	<li class="dropdown">
                       <a href="#" class="dropdown-toggle" data-toggle="dropdown">学生信息管理 <b class="caret"></b></a>
                       <ul class="dropdown-menu">
                         <li><a href="studentPreSave">学生信息添加</a></li>
                         <li><a href="studentList">学生信息维护</a></li>
                       </ul>
                   </li>
		      	<li class="dropdown">
                       <a href="#" class="dropdown-toggle" data-toggle="dropdown">班级信息管理 <b class="caret"></b></a>
                       <ul class="dropdown-menu">
                         <li><a href="classPreSave">班级信息添加</a></li>
                         <li><a href="classList">班级信息维护</a></li>
                       </ul>
                   </li>
                   <li class="dropdown">
                       <a href="#" class="dropdown-toggle" data-toggle="dropdown">年级信息管理 <b class="caret"></b></a>
                       <ul class="dropdown-menu">
                         <li><a href="gradePreSave">年级信息添加</a></li>
                         <li><a href="gradeList">年级信息维护</a></li>
                       </ul>
                   </li>
		        <li class="dropdown">
                       <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理 <b class="caret"></b></a>
                       <ul class="dropdown-menu">
                         <li><a href="dataDicList">数据字典维护</a></li>
                         <li><a href="dataDicTypeList">数据字典类别维护</a></li>
                         <li><a href="userPreSave">修改密码</a></li>
                         <li class="divider"></li>
                         <li><a href="javascript:logOut()">退出系统</a></li>
                       </ul>
                   </li>
		    </ul>
		  </div>
		</div>
	</div>
</div>