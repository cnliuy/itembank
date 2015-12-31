package org.liuy.music.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.ueditor.ActionEnter;

/**
 * Ueditor 的 Restful API的Controller.
 * 
 * @author liuy
 */
@RestController
@RequestMapping(value = "/ueditor")
public class UeditorRestController {
	
	//http://localhost:8080/itembank/static/ueditorjs/gotaskcontroll
//	@RequestMapping(value = "gotaskcontroll" )
//	public String gotaskcontroller(ServletRequest req ,ServletResponse resp) {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		System.out.println("----------- gotaskcontroller------------"+new Date());
//		//model.addAttribute("task", new Task());
//		//model.addAttribute("action", "gocreate");
//		//return "task/gocreate";
//		try {
//			request.setCharacterEncoding( "utf-8" );
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		response.setHeader("Content-Type" , "text/html");		
//		//String rootPath = application.getRealPath( "/" );
//		//String herePath = request.getSession().getServletContext().getRealPath("/");
//		String herePath = UrlUtil.getClassFilePath(UeditorRestController.class);
//		herePath=herePath.replace("UeditorRestController.class", "");
//		System.out.println(herePath);
//		String jsonfile = "";
//		try {
//			jsonfile = new ActionEnter( request, herePath ).exec();
//			System.out.println(jsonfile);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//	out.write( new ActionEnter( request, rootPath ).exec() );
//		
//	    //return this.parentPath + File.separator + ConfigManager.configFileName;
//        //I modify this, because it is not suit for our porject.
//
//		return jsonfile;
//	}
	
	/**
	 * http://localhost:8080/itembank/ueditor/dispatch
	 * 
	 * */
	@RequestMapping("/dispatch")
    public void config(HttpServletRequest request,  HttpServletResponse response, String action) {
             response.setContentType("application/json");               
             String rootPath = request.getSession().getServletContext().getRealPath("/");
             //System.out.println("-----------:"+rootPath);
             try {
                    String exec="";
					try {
						exec = new ActionEnter(request, rootPath).exec();
	                    PrintWriter writer = response.getWriter();
	                    writer.write(exec);
	                    writer.flush();
	                    writer.close();
					} catch (JSONException e) {
						System.out.println("org.liuy.music.rest 中 UeditorRestController 发生异常");
						e.printStackTrace();
					}

             } catch (IOException e) {
                     e.printStackTrace();
             }
             
     }

}
