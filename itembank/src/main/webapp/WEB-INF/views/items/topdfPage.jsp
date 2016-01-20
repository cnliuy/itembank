<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>PDF生成</title>
</head>

<body>
	<c:if test="${not empty messagesucc}">
		<div id="messagesucc" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${messagesucc}</div>
		
		<div><a href="${ctx}/static/temphtml/${pdfFilename}.pdf" target="_blank" class="btn">PDF文件下载</a></div>	
	</c:if>
	
	
	<c:if test="${not empty messagefail}">
		<div id="messagefail" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${messagefail}</div>
	</c:if>	

</body>
</html>
