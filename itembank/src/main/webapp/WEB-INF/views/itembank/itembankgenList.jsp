<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>题目生成</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
	    <tags:sort/>
	</div>
	 
	<form id="inputForm" action="${ctx}/itembank/genitembankList" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">需生成题目数（数字）:</label>
				<div class="controls">
					<input type="text" value="" class="input-large" name="itemnum" id="itemnum"  />
				</div>
		</div>
		
		<div class="form-actions">
			<input id="submit_btn" class="btn btn-primary" type="submit" value="生成题目"  />
			<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
	</form>
	<label class="control-label">生成题目如下:</label><br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<c:forEach items="${itembanks}" var="itembank">
		${itembank.content}<br>
	</c:forEach>
	
	</table>
	<input id="submit_btn" class="btn btn-primary" type="submit" value="生成word文件"  />

	

</body>
</html>
