<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>题库管理</title>
</head>

<body>

	<script language="javascript"> 
	//删除确认
	function del_sure(){ 
		var gnl=confirm("您真的确定要删除吗？\n\n删除后将不能恢复!"); 
		if (gnl==true){ 
			return true; 
		}else{ 
			return false; 
		} 
	} 
	</script> 

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>id</th><th>题目</th><th>答案</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${itembanks.content}" var="itembank">
			<tr>
				<td><a href="${ctx}/itembank/showdetail/${itembank.id}">${itembank.id}</a></td>
				<td>${itembank.content}</td><td>${itembank.itemanswer}</td>
				<td><a href="${ctx}/itembank/showdetail/${itembank.id}">查看</a></td>
				<td><a href="${ctx}/itembank/delete/${itembank.id}" style="color:red;" 
	                    	onclick="javascript:return del_sure()" >删除</a></td>
				<td><a href="${ctx}/itembank/toupdate/${itembank.id}">修改</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${itembanks}" paginationSize="10"/>
	<!--  
	<div><a class="btn" href="${ctx}/task/gocreate">创建任务2</a></div><br>
	<div><a class="btn" href="${ctx}/task/create">创建任务</a></div>	
	<div><a class="btn" href="${ctx}/itembank">题目列表</a></div>
	-->
	<br>
	&nbsp;
	<div><a href="${ctx}/itembank/tocreateitembankpage"  class="btn" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建题目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></div> &nbsp;<br>
	<div><a href="${ctx}/itembank/tocreateitembankListpage" class="btn" >按需随机生成题目</a></div> &nbsp;
	<div><a href="${ctx}/itembank">返回题目列表</a></div>
	
</body>
</html>
