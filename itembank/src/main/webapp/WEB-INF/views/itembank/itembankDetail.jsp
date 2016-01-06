<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>题目详情</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.parse.min.js"> </script>

	<!-- 
    <style type="text/css">
        div{
            width:100%;
        }
    </style> 
    -->
	
	<!-- my add jquery -->
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
	
</head>

<body>
<div>
    <!-- <h1>完整demo</h1> -->
    <script id="editor" type="text/plain" style="width:1024px;height:250px;"></script>
</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	 
	<c:if test="${not empty itembank}">
		<div class="control-group">
				<label for="task_title" class="control-label">题目内容:</label>
				<div class="controls">
				${itembank.content}				 
				</div>
		</div>
		<br></br>
		<div class="control-group">
				<label for="task_title" class="control-label">题目分类:</label>
				<div class="controls">${itembank.itemclassify}</div>
		</div>	
		<div class="control-group">
				<label for="task_title" class="control-label">题目描述:</label>
				<div class="controls">${itembank.description}</div>
		</div>		
		<div class="form-actions">
			<div><a class="btn" href="${ctx}/itembank/toupdate/${itembank.id}">修改题目</a></div>
			<div><a class="btn" href="${ctx}/itembank">题目列表</a></div>
		</div>		
	</c:if>
	
	
</body>
</html>