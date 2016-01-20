<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>随机题目管理</title>
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
		<thead><tr><th>标示</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${items.content}" var="item">
			<tr>				
				<td>${item.title}</td>
				<td><a href="${ctx}/items/showdetail/${item.id}">查看</a></td>
				<td><a href="${ctx}/items/delete/${item.id}" style="color:red;" 
	                    	onclick="javascript:return del_sure()" >删除</a></td>
				<td><a href="${ctx}/items/toupdate/${item.id}">修改</a></td>
				<td><a href="${ctx}/items/togenhtml/${item.id}"  target="_blank">生成Html</a></td>
				<td><a href="${ctx}/items/togenpdf/${item.id}"   target="_blank">生成PDF</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${items}" paginationSize="10"/>

	<br>
	<div><a href="${ctx}/itembank">返回题库题目列表</a></div>
	&nbsp;
	<div><a href="${ctx}/itembank/tocreateitembankListpage" class="btn" >按需随机生成题目</a></div> &nbsp;
	
	<div><a href="${ctx}/items">返回随机题目列表</a></div>
	
</body>
</html>
