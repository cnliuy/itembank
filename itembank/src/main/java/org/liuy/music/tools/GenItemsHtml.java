package org.liuy.music.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

public class GenItemsHtml {

	/**
	 * 为 Items 生成指定的 html
	 * 	@param
	 * 		ctx :  request.getContextPath();	不带后缀的。
	 *  	realPath : request.getRealPath("/")+"static"+File.separator+"temphtml"+File.separator;
	 *  
	 *  @return  -1 文件失败
	 *  		  1 文件生成成功
	 * 
	 * */
	public static int GenHtmlForItem(String filenamecore, String ctx , String realPath ,String contents){  
		int flag = -1 ;
				
		String htmlstr = "<!DOCTYPE html>"
		+ "<html>\r\n"
		+ "<head>\r\n"
		+ "<title></title>\r\n"
		+ "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\r\n"
		+ "<link type=\"image/x-icon\" href=\"${ctx}/static/images/favicon.ico\" rel=\"shortcut icon\">\r\n"
		+ "<link href=\""+ctx+"/static/bootstrap/2.3.2/css/bootstrap.min.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n"
		+ "<link href=\""+ctx+"/static/jquery-validation/1.11.1/validate.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n"
		+ "<link href=\""+ctx+"/static/styles/default.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n"
		+ "<link href=\"http://twitter.github.com/bootstrap/assets/js/google-code-prettify/prettify.css\" rel=\"stylesheet\">\r\n"
		+ "<link href=\"http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css\" rel=\"stylesheet\">\r\n"
		+ "<link href=\"http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css\" rel=\"stylesheet\">\r\n"
		+ "<link href=\"http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css\" rel=\"stylesheet\">\r\n"
		+ "<script src=\""+ctx+"/static/jquery/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/jquery/jquery.hotkeys.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/google-code-prettify/prettify.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/jquery-validation/1.11.1/jquery.validate.min.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/jquery-validation/1.11.1/messages_bs_zh.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/bootstrap/2.3.2/js/bootstrap.min.js\" type=\"text/javascript\"></script>\r\n"
		+ "<script src=\""+ctx+"/static/bootstrap/2.3.2/js/bootstrap-wysiwyg.js\" type=\"text/javascript\"></script>\r\n"
	    + "</head>\r\n"
	    + "<body>\r\n"
	    + "	<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">\r\n"
	    + contents
		+ "	</table>\r\n"
		+ "</body>\r\n"
		+ "</html>"	;		 			
	 
		System.out.println("realPath:"+realPath);
		String message = "题目生成成功" ;			
		
		String s1 ="";
		String filename ="";
		try{			 	
			filename = filenamecore+".html";		   
			FileOutputStream fos = new FileOutputStream(realPath+filename); 
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
			osw.write(htmlstr); 
			osw.flush(); 
			osw.close();			 		
			flag = 1 ; //写入文件成功			 
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return   flag ;
		
		
	}
}
