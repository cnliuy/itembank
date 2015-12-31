<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	//已经废弃 不用，现使用 org.liuy.music.rest UeditorRestController类的 config()方法
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");	
	//String rootPath = application.getRealPath( "/static/ueditorjs/" );
	String rootPath = application.getRealPath( "/ueditor" );
	 
	//System.out.println("rootPath:"+rootPath);
	String s = new ActionEnter( request, rootPath ).exec();
		
	out.write( s );
	
%>