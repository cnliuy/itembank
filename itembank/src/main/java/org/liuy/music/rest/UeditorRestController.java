package org.liuy.music.rest;

import java.io.BufferedReader;
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

	
	
	
	/**
	 * 方法1.
	 * 
	 * http://localhost:8080/itembank/ueditor/gogetuecontent
	 * AJAX方式传值   js直接传值到后端。
	 *    原始方法获取值，参考文章  http://blog.csdn.net/mhmyqn/article/details/25561535
	 * 
	 * 
	 * */
	@RequestMapping("/gogetuecontent")

    public void gogetuecontent(HttpServletRequest request,  HttpServletResponse response) {
		//String uecontent = "";
		//String uecontent2 = "";
		//uecontent =	(String) request.getAttribute("uecontent"); 
		//System.out.println("uecontent:"+uecontent);
		//uecontent2 = request.getParameter("uecontent"); 
		//System.out.println("uecontent2:"+uecontent2);
		//try {
		//	String s = request.getInputStream().toString();
		//	System.out.println("allrequest:"+s);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
		/**************************************************/
		//--------------AJAX POST请求中参数以form data和request payload形式在servlet中的获取方式
		//-------------原始方法获取
		// http://blog.csdn.net/mhmyqn/article/details/25561535
		StringBuilder sb = new StringBuilder();  
        try(
        	BufferedReader reader = request.getReader();
        ){
        	char[]buff = new char[1024];  
            int len;  
            while((len = reader.read(buff)) != -1) {  
            	sb.append(buff,0, len);  
            }
            
         }catch (IOException e) {  
            e.printStackTrace();  
         }
        System.out.println(sb.toString());
		
	}
	
	
	/**
	 * 方法2.
	 * 
	 * http://localhost:8080/itembank/ueditor/gogetuecontent2
	 * 通过Post提交上来的参数 ，在表单提交后，通过js修改提交的数据。
	 * 	对应 taskForm2.jsp 页面中的表单提交后，修改表单中属性值的 js代码
	 * 
	 * 	function changeContent(form) {
	 * 		alert("here1111");     
	 * 		var strurcontent = UE.getEditor('editor').getContent();
	 * 		alert(strurcontent); 
	 * 		//form.uepostcontent.value= strurcontent ;   
	 * 		document.getElementById("uepostcontent").value=strurcontent;
	 * 		form.submit();
	 * 	}
	 * 
	 * form通过js修改提交服务器的值。实测可用
	 *
	 * 
	 * */
	@RequestMapping("/gogetuecontent2")
    public void gogetuecontent2(HttpServletRequest request,  HttpServletResponse response) {
		System.out.println("gogetuecontent2");
		String uecontent = "";
		String uecontent2 = "";
		uecontent =	(String) request.getAttribute("uepostcontent"); 
		System.out.println("uecontent:"+uecontent);
		uecontent2 = request.getParameter("uepostcontent"); 
		System.out.println("uecontent2:"+uecontent2);
		
	}
}
