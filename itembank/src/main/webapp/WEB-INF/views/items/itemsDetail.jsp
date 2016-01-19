<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>随机题目详情</title>
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
    
    <script id="editor" type="text/plain" style="width:1024px;height:250px;"></script>
</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	 
	<c:if test="${not empty items}">
		<div class="control-group">
				<label for="task_title" class="control-label"> <B>随机题目内容:</B></label>
				<div class="controls">
				${items.contents}				 
				</div>
		</div>
		<br></br>
		<div class="control-group">
				<label for="task_title" class="control-label"> <B>题目标示:</B></label>
				<div class="controls">${items.title}</div>
		</div>	
		
	
		<div class="form-actions">
			<div><a class="btn" href="${ctx}/items/toupdate/${items.id}">修改随机题目</a></div>
			<div><a class="btn" href="${ctx}/items">随机题目列表</a></div>
		</div>		
	</c:if>
	
	
</body>
</html>