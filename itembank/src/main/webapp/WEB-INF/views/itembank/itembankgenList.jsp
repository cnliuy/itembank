<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>题目生成</title>
</head>

<body>

<script type="text/javascript">

     window.onload = $(function(){
    			//获取题目类型的分类数据  写入相应的 多选框中         	
                $.getJSON("${ctx}/itembankrest/gogetitembankkinds",function(data){
                    var $jsontip = $("#itemclassifyoption");
                    var strHtml = "<select class=\"input-large required\" name=\"itemclassify\" id=\"itemclassify\">	";
                    <c:if test="${not empty itemclassify}">
                    strHtml += "<option  selected=\"selected\"  value =\"${itemclassify}\">${itemclassify}</option>" ;   
                    strHtml += "<option  value =\"全部\">全部</option>" ;  
                    </c:if>
                    <c:if test="${empty itemclassify}">
                    strHtml += "<option  selected=\"selected\"  value =\"全部\">全部</option>" ;  
                    </c:if>
                    //alert(data);
                    $jsontip.empty();
                    $.each(data,function(infoIndex,info){
                    	//alert(infoIndex); 
                    	
                    	//if(infoIndex == 0){
                    	//	strHtml += "<option  selected=\"selected\">"+info["title"]+"</option>";                    		
                    	//}else{
                    		var stt = info["title"];
                    		strHtml += "<option value =\""+stt+"\">"+stt+"</option>";
                    		 //alert("--"+info["title"]); 
                    	//} 
                    })
                    strHtml += "</select>" ;
                    //alert(strHtml); 
                    $jsontip.html(strHtml); 

                })

           		//获取题目知识点的分类数据  写入相应的 多选框中        
              $.getJSON("${ctx}/itembankrest/gogetitembankranges",function(data2){
                    var $jsontip2 = $("#itemrange1option");
                    var strHtml2 = "<select class=\"input-large required\" name=\"itemrange1\" id=\"itemrange1\">";
                    strHtml2 += "<option  selected=\"selected\"  value =\"${itemrange1}\">${itemrange1}</option>" ;    
                    //alert(data);
                    <c:if test="${not empty itemrange1}">
                    strHtml2 += "<option  selected=\"selected\"  value =\"${itemrange1}\">${itemrange1}</option>" ;   
                    strHtml2 += "<option  value =\"全部\">全部</option>" ;  
                    </c:if>
                    <c:if test="${empty itemrange1}">
                    strHtml2 += "<option  selected=\"selected\"  value =\"全部\">全部</option>" ;  
                    </c:if>
                    
                    $jsontip2.empty();
                    $.each(data2,function(infoIndex,info){
                    	//alert(infoIndex);                  
                    	//if(infoIndex == 0){
                    		//strHtml2 += "<option  selected=\"selected\">"+info["title"]+"</option>";                    		
                    	//}else{
                    		//strHtml2 += "<option>"+info["title"]+"</option>";
                    		var stt = info["title"];
                    		strHtml2 += "<option value =\""+stt+"\">"+stt+"</option>";
                            //strHtml += "id:"+info["id"]+"<br>";
                            //strHtml += "<br>";
                            //strHtml += "<hr>"
                    	//} 
                    })
                    strHtml2 += "</select>" ;
                    //alert(strHtml2); 
                    $jsontip2.html(strHtml2); 
                })
                
                

            //获取题目等级的分类数据  写入相应的 多选框中        
            $.getJSON("${ctx}/itembankrest/gogetitembanklevel",function(data3){
                var $jsontip3 = $("#itemrange2option");
                var strHtml3 = "<select class=\"input-large required\" name=\"itemrange2\" id=\"itemrange2\">";
                strHtml3 += "<option  selected=\"selected\" value =\"${itemrange2}\">${itemrange2}</option>" ;   
                //alert(data);
                <c:if test="${not empty itemrange2}">
                strHtml3 += "<option  selected=\"selected\"  value =\"${itemrange2}\">${itemrange2}</option>" ;   
                strHtml3 += "<option  value =\"全部\">全部</option>" ;  
                </c:if>
                <c:if test="${empty itemrange2}">
                strHtml3 += "<option  selected=\"selected\"  value =\"全部\">全部</option>" ;  
                </c:if>
                
                
                $jsontip3.empty();
                $.each(data3,function(infoIndex,info){
                	//alert(infoIndex);                  
                	//if(infoIndex == 0){
                	//	strHtml3 += "<option  selected=\"selected\">"+info["title"]+"</option>";                    		
                	//}else{
                		//strHtml3 += "<option>"+info["title"]+"</option>";
                		var stt = info["title"];
                		strHtml3 += "<option value =\""+stt+"\">"+stt+"</option>";
                	//} 
                })
                strHtml3 += "</select>" ;
                $jsontip3.html(strHtml3); 
            })
                
        })
    </script> 



	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
	    <tags:sort/>
	</div>
	 
	<form id="inputForm" action="${ctx}/itembank/genitembankList" method="post" class="form-horizontal">
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
			<label for="task_title" class="control-label">题目范围:</label>
			<div class="controls">
				<div id = "itemrange1option"></div>
				<!--  				
				<select class="input-large required" name="itemrange1" id="itemrange1">
                	<option  selected="selected">第一学年</option>
                    <option>第二学年</option>
                    <option>第三学年</option>
                    <option>第四学年</option>
                </select>
                -->	
			</div><br>
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
	
	
	
		<div class="control-group">
			<label class="control-label">需生成题目数（数字）:</label>
				<div class="controls">
					<input type="text"   class="input-large" name="itemnum" id="itemnum" value="${itemnum}" />
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
	

	<br>
	
	<c:if test="${not empty filename}">
		<div><a href="${ctx}/static/temphtml/${filename}" target="_blank"  class="btn">生成HTML文件</a></div>
		
		<div><a href="${ctx}/itembank/genPdfFile?pdfFilename=${filename}" target="_blank" class="btn">生成PDF文件</a></div>
		
	</c:if>
	
	
	

</body>
</html>
