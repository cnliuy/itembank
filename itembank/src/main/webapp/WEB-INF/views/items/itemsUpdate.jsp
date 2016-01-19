<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>修改随机题目</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script>window.PROJECT_CONTEXT = "${ctx}/";</script>
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
	
	
    


<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    //function setblur(e){
    //    UE.getEditor('editor').blur();
    //    UE.dom.domUtils.preventDefault(e)
    //}
    //function insertHtml() {
    //    var value = prompt('插入html代码', '');
    //    UE.getEditor('editor').execCommand('insertHtml', value)
    //}


    //function getContent() {
    //    var arr = [];
    //    arr.push("使用editor.getContent()方法可以获得编辑器的内容");
    //    arr.push("内容为：");
    //    var strurcontent = UE.getEditor('editor').getContent();
    //    arr.push(strurcontent);
    //    alert(arr.join("\n"));        
    //    //liuy add
    //    var xhr = new XMLHttpRequest();  
	//	var formData = new FormData();  
	//	var urlhere = "${ctx}/itembank/postuecontent";
	//	formData.append("uecontent", strurcontent);  
	//	//xhr.open('post', Core.host + '/test/test?id=' + id, true);
	//	xhr.open('post', urlhere, true); 
	//	alert(urlhere);
	//	alert(formData);
	//	xhr.send(formData);         
    //    //request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');  
    //}
    
    function gogetueditorContent(form) {
    	//alert("here1111");     
        var strurcontent = UE.getEditor('editor').getContent();
        //alert(strurcontent); 
        //form.uepostcontent.value= strurcontent ;   
        document.getElementById("uepostcontent").value=strurcontent;
		form.submit();
    }
 
    

    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }


    function setFocus() {
        UE.getEditor('editor').focus();
    }


    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }
    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }
    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }
</script>
 	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	 

	<div>
    	<!-- <h1>完整demo</h1> -->
    	<script id="editor" type="text/plain" style="width:1024px;height:180px;">${items.contents}</script>
	</div>
	<c:if test="${not empty items}">
	<form action="${ctx}/items/updateitemscontent"	method="post"  >
		<input type="hidden" id="itemsid"  name="itemsid" value="${items.id}"/>	 
		<input type="hidden" id="uepostcontent"  name="uepostcontent" value=""/>		
		<br></br>		
		<div class="control-group">
			<label for="task_title" class="control-label">标注（以此标识内容）:</label>
			<div class="controls">			
				<input type="text" id="title"  name="title" value="${items.title}"/>	
			</div>
		</div>
	 	<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="保存" onclick="gogetueditorContent(this.form)"/>
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
		
	</form>
	</c:if>
	
</body>
</html>