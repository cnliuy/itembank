<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>getJSON</title>
    <style type="text/css">

        #divframe{ border:1px solid #999; width:500px; margin:0 auto;}
        .loadTitle{ background:#CCC; height:30px;}

    </style>
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery/jquery.hotkeys.js" type="text/javascript"></script>

    <script type="text/javascript">
    //window.onload = function(){ 
    //$(function(){
     window.onload = $(function(){
            //$("#btn").click(function(){
                $.getJSON("${ctx}/itembankrest/gogetitembankkinds",function(data){
                    var $jsontip = $("#jsonTip");
                    var strHtml = "123";
                    //alert(data);
                    $jsontip.empty();
                    $.each(data,function(infoIndex,info){
                        strHtml += "titles:"+info["title"]+"<br>";
                        strHtml += "id:"+info["id"]+"<br>";
                        strHtml += "<br>";
                        strHtml += "<hr>"
                    })
                    $jsontip.html(strHtml); 

                })
           // })
        })
    </script>

</head>



<body>

<div id="divframe">
	
    <div class="loadTitle">
        <input type="button" value="获取数据" id="btn"/>
    </div>

    <div id="jsonTip">

    </div>

</div>

</body>

</html> 