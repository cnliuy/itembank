<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>完整demo</title>
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
	
	<script type="text/javascript">
	//获取题目类型的分类数据  写入相应的 多选框中
    //window.onload = function(){ 
    //$(function(){
     window.onload = $(function(){
            //$("#btn").click(function(){           	
                $.getJSON("${ctx}/itembankrest/gogetitembankkinds",function(data){
                    var $jsontip = $("#itemclassifyoption");
                    var strHtml = "<select class=\"input-large required\" name=\"itemclassify\" id=\"itemclassify\">	";
                    //alert(data);
                    $jsontip.empty();
                    $.each(data,function(infoIndex,info){
                    	//alert(infoIndex);                  
                    	if(infoIndex == 0){
                    		//strHtml += "<option  selected=\"selected\">"+info["title"]+"</option>";
                    		var stt = info["title"];
                    		strHtml += "<option  selected=\"selected\" value =\""+stt+"\">"+stt+"</option>";         		
                    	}else{
                    		//strHtml += "<option>"+ info["title"]+"</option>";                    		
                     		var stt = info["title"];
                    		strHtml += "<option value =\""+stt+"\">"+stt+"</option>";
                            //strHtml += "id:"+info["id"]+"<br>";
                            //strHtml += "<br>";
                            //strHtml += "<hr>"
                    	} 
                    })
                    strHtml += "</select>" ;
                    //alert(strHtml); 
                    $jsontip.html(strHtml); 

                })
           // })
           
              $.getJSON("${ctx}/itembankrest/gogetitembankranges",function(data2){
                    var $jsontip2 = $("#itemrange1option");
                    var strHtml2 = "<select class=\"input-large required\" name=\"itemrange1\" id=\"itemrange1\">";
                    //alert(data);
                    $jsontip2.empty();
                    $.each(data2,function(infoIndex,info){
                    	//alert(infoIndex);                  
                    	if(infoIndex == 0){
                    		//strHtml2 += "<option  selected=\"selected\">"+info["title"]+"</option>";   
                    		var stt = info["title"];
                    		strHtml2 += "<option  selected=\"selected\" value =\""+stt+"\">"+stt+"</option>"; 
                    	}else{
                    		//strHtml2 += "<option>"+info["title"]+"</option>";
                    		var stt = info["title"];
                    		strHtml2 += "<option value =\""+stt+"\">"+stt+"</option>";
                            //strHtml += "id:"+info["id"]+"<br>";
                            //strHtml += "<br>";
                            //strHtml += "<hr>"
                    	} 
                    })
                    strHtml2 += "</select>" ;
                    //alert(strHtml2); 
                    $jsontip2.html(strHtml2); 
                })
                
                

            
            $.getJSON("${ctx}/itembankrest/gogetitembanklevel",function(data3){
                var $jsontip3 = $("#itemrange2option");
                var strHtml3 = "<select class=\"input-large required\" name=\"itemrange2\" id=\"itemrange2\">";
                //alert(data);
                $jsontip3.empty();
                $.each(data3,function(infoIndex,info){
                	//alert(infoIndex);                  
                	if(infoIndex == 0){
                		//strHtml3 += "<option  selected=\"selected\">"+info["title"]+"</option>";    
                		var stt = info["title"];
                		strHtml3 += "<option  selected=\"selected\" value =\""+stt+"\">"+stt+"</option>"; 
                	}else{
                		//strHtml3 += "<option>"+info["title"]+"</option>";
                		var stt = info["title"];
                		strHtml3 += "<option value =\""+stt+"\">"+stt+"</option>";
                	} 
                })
                strHtml3 += "</select>" ;
                $jsontip3.html(strHtml3); 
            })
                
        })
    </script>    
    
    
    
    
	
</head>

<body>
<div>
    <!-- <h1>完整demo</h1> -->
    <script id="editor" type="text/plain" style="width:1024px;height:180px;"></script>
</div>


<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    //function insertHtml() {
    //    var value = prompt('插入html代码', '');
    //    UE.getEditor('editor').execCommand('insertHtml', value)
    //}
    //function createEditor() {
    //    enableBtn();
    //    UE.getEditor('editor');
    //}
    //function getAllHtml() {
    //    alert(UE.getEditor('editor').getAllHtml())
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
 
    
    //function getPlainTxt() {
    //    var arr = [];
    //    arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
    //    arr.push("内容为：");
    //    arr.push(UE.getEditor('editor').getPlainTxt());
    //    alert(arr.join('\n'))
    //}
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }
    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }
    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }
    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
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


	<form action="${ctx}/itembank/postuecontent"	method="post"  > 
		<input type="hidden" id="uepostcontent"  name="uepostcontent" value=""/>
		<br></br>
		<div class="control-group">
				<label for="task_title" class="control-label">题目类型:</label>
				<div class="controls">									
					<div id = "itemclassifyoption"></div>
					<!--
						<option  selected="selected">单选题</option>
                        <option>多选题</option> 
					 -->                    
				</div>
		</div>
		<div class="control-group">
				<label for="task_title" class="control-label">题目答案:</label>
				<div class="controls">
					<input type="text" id="itemanswer" name="itemanswer"/>
				</div>
		</div>

		<div class="control-group">
				<label for="task_title" class="control-label">题目范围:</label>
				<div class="controls">			
						
					
					<div id = "itemrange1option">
					<!--
					<select class="input-large required" name="itemrange1" id="itemrange1">
                        <option  selected="selected">第一学年</option>
                        <option>第二学年</option>
                        <option>第三学年</option>
                        <option>第四学年</option>
                     </select>
                     -->
                    </div>
                    
				</div>
				<div class="controls">

					<div id = "itemrange2option"></div>
					<!--  
					<select class="input-large required" name="itemrange2" id="itemrange2">
                        <option  selected="selected">第一学期</option>
                        <option>第二学期</option>
                    </select>
                    --> 
				</div>
		</div>	

	 	<!--  
		<div class="control-group">
				<label for="task_title" class="control-label">题目范围:</label>
				<div class="controls">
					<input type="text" id="description" name="description"     />
				</div>
		</div>
		-->
	 	<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交" onclick="gogetueditorContent(this.form)"/>
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
		</div>
		
	</form><br>
	<div><a href="${ctx}/itembank">返回题目列表</a></div>
	
</body>
</html>