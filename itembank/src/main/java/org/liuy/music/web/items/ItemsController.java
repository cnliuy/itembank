/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.liuy.music.web.items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Items;
import org.liuy.music.entity.ReturnResponse;
import org.liuy.music.entity.Task;
import org.liuy.music.entity.User;
import org.liuy.music.service.Itembank.IaccessauthService;
import org.liuy.music.service.Itembank.ItembankService;
import org.liuy.music.service.Itembank.ItemsService;
import org.liuy.music.service.account.ShiroDbRealm.ShiroUser;
import org.liuy.music.service.task.TaskService;
import org.liuy.music.tools.GenItemsHtml;
import org.liuy.music.tools.HtmlToPdf;
import org.liuy.music.tools.JacksonUtil;
import org.liuy.music.web.itembank.ItemidModel;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * Items管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /items/
 * Create page : GET /items/create
 * Create action : POST /items/create
 * Update page : GET /items/update/{id}
 * Update action : POST /items/update
 * Delete action : GET /items/delete/{id}
 * 
 * @author liuy
 */
@Controller
@RequestMapping(value = "/items")
public class ItemsController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ItemsService itemsService;
	@Autowired
	private ItembankService itembankService;
	
	@Autowired
	private IaccessauthService iaccessauthService;
	
	
	/**
	 * 根据范围和数目 生成题目列表
	 *  参考
	 *  http://www.tuicool.com/articles/UjaeUj 
	 *  Spring mvc 接收页面表单List 
	 * */
	@RequestMapping(value = "gogetGenItemList" )
	public String gogetGenItemList(ItemidModel itemidModel , Model model ) {	
		String s= JacksonUtil.toJSon(itemidModel);	 //接收表单List 内容
		List itrmids = itemidModel.getItemid() ;
		
		Iterator  itrmidsi = itrmids.iterator();
		System.out.println("--------------" );	
		
		List <Itembank> ibs = new ArrayList(); //需向其中添加内容 加肉
		Long tmpId ;
		Itembank tmpib ;
		String contents = "" ;
		while(itrmidsi.hasNext()){
			tmpId  = Long.valueOf((String)(itrmidsi.next())) ;
			tmpib = itembankService.getItembank(tmpId) ;
			ibs.add(tmpib);	
			contents=contents+tmpib.getContent()+"<br>";				
		} 
		
		
		//String filenamecore = UUID.randomUUID().toString(); //不带后缀的文件名
		
		
		model.addAttribute("contents", contents);		
		//model.addAttribute("filenamecore", filenamecore);	
		return "items/itemsCreate";
	}
	
	/**
	 * 更新items的内容
	 * 
	 * 
	 * */
	@RequestMapping(value = "createitemscontent" )
	public String createitemscontent(HttpServletRequest request,  HttpServletResponse response , Model model ) {	

		String uecontent = request.getParameter("uepostcontent");	
		String title = request.getParameter("title");
		System.out.println("title------"+title);		
		Long userId = getCurrentUserId();
		Items items = new Items();
		items.setContents(uecontent);
		items.setTitle(title);
		items.setUserId(userId);
		items.setNeedfile(0);
		itemsService.saveItems(items);	
		return "redirect:/items/";
	}
	
	
	/**
	 * 更新内容 
	 * 
	 *  ---修改需鉴权
	 * 
	 * */
	@RequestMapping("/updateitemscontent")
    public String updateitemscontent(HttpServletRequest request,  HttpServletResponse response , Model model ,RedirectAttributes redirectAttributes) {
		//System.out.println("in  itembankController() postuecontent");
		//String uecontent =	(String) request.getAttribute("uepostcontent");   
		//System.out.println("uecontent:"+uecontent); //空值
		String uecontent = request.getParameter("uepostcontent");
		String title = request.getParameter("title");	
		String itemsid = request.getParameter("itemsid");	
		 
		System.out.println("title:"+title);		
		Long userId = getCurrentUserId();
		
		Items items = null;
		if(itemsid == null || "".equals(itemsid)) {
			//model.addAttribute("message", "不存在此题目");
			redirectAttributes.addFlashAttribute("message", "不存在此题目");
		}else{
			try {
				Long itemsIdl= Long.parseLong(itemsid); 
				items = itemsService.getItems(itemsIdl);			 
				Long CurrentUserId = getCurrentUserId();
				ReturnResponse rr = iaccessauthService.checkItemsUserUpdate(CurrentUserId, itemsIdl);
				
				int retCode = rr.getRetCode() ;
				String retInfo  =rr.getRetInfo();
				int nofile = 0 ; //不存在新的html
				
				if(retCode == IaccessauthService.rCanDo){
					items.setContents(uecontent);
					items.setTitle(title);
					items.setNeedfile(nofile);
					items.setFilename("");
					//items.setUserId(CurrentUserId);  -- 不能要  注意					
				 	itemsService.saveItems(items);				
				}
				redirectAttributes.addFlashAttribute("message", retInfo);
			} catch (Exception e) {			
				redirectAttributes.addFlashAttribute("message", "参数错误，String到Long转化失败");		
			}
			
		}
		return "redirect:/items/";
	}
	
	
	
	 
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();
		Page<Items> items = itemsService.getUserItemsList(userId, pageNumber, pageSize, sortType);
		model.addAttribute("items", items);
		model.addAttribute("sortType", sortType);
		return "items/itemsList";
	}
	
	/**
	 * 根据items 生成html
	 * 
	 * ---生成需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value ="/togenhtml/{id}" , method = RequestMethod.GET) 
	public String togenhtml(@PathVariable("id") Long itemsid, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes ) {
		Items items =  null ;
		String  itemscontent =  "" ;
		Boolean needwirteFileFlag = true; //true  不存在文件 需要写入文件 ; false 不需要写
		int existFile = 1 ;
		
		items = itemsService.getItems(itemsid)  ; 
		if(items == null ){
			model.addAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();
			Long itemsId =  items.getId() ;
			ReturnResponse rr = iaccessauthService.checkItemsUserRead(CurrentUserId, itemsId);
			int retCode = rr.getRetCode() ;
			String retInfo  =rr.getRetInfo();
			if(retCode == IaccessauthService.rCanDo){
				itemscontent=items.getContents();
				if (items.getNeedfile() == existFile ) {
					needwirteFileFlag = false ;
				}
				model.addAttribute("items", items);			
			}else{
				model.addAttribute("message", retInfo);	
			}
		}
		
		/**
		 * 写html文件  也写pdf  ---- 先html 再pdf
		 * 
		 * 
		 * */		
		if("".equals(itemscontent) || itemscontent == null  ){
			
			return "redirect:/items/";
			
		}else if( !needwirteFileFlag){
			//已有文件 不需要写入了 
			return "redirect:/static/temphtml/"+items.getFilename()+".html";
			
		}else{
			//没有文件 需要写入
			String ctx = request.getContextPath();			
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
		    + itemscontent
			+ "	</table>\r\n"
			+ "</body>\r\n"
			+ "</html>"	;
			
			String realPath = request.getRealPath("/")+"static"+File.separator+"temphtml"+File.separator;			
			//System.out.println(htmlstr);
			System.out.println("realPath:"+realPath);
			String message = "题目生成成功" ;			
			
			String s1 ="";
			String filename ="";
			try{			
				s1 = UUID.randomUUID().toString();		
				filename = s1+".html";		   
				FileOutputStream fos = new FileOutputStream(realPath+filename); 
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
				osw.write(htmlstr); 
				osw.flush(); 
				osw.close();			
				model.addAttribute("filename", filename);			
				message = message + " html页面生成成功 " ;	
				
				//--------  生成pdf ------
				String filename21 =  realPath+s1+".pdf" ;				
				//url完整链接获取 获取网络的地址
				String urlpathpart = request.getContextPath();  
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+urlpathpart+"/"; 			
				String pageurl = basePath+"/static/temphtml/"+s1+".html ";			
				String messagesucc = "";
				String messagefail = "";
				if( HtmlToPdf.convert(pageurl,filename21) ){
					messagesucc = "成功生成文件"; //生成pdf成功
					
					//生成了 html 和 pdf 双重文件					
					//model.addAttribute("messagesucc", messagesucc);
					model.addAttribute("message", message);		
					//存入items的对象中
					int needfile = 1 ;
					items.setNeedfile(needfile);
					items.setFilename(s1);				
					itemsService.saveItems(items);
					//System.out.println("生成文件成功 ");				
				}else{
					message = "文件生成出错  错误码 01201336" ;// html文件生成ok ，pdf生成失败 
					model.addAttribute("message", message);		
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			return "redirect:/static/temphtml/"+items.getFilename()+".html";
					
		}
		
	}
	
	
	
	
	
	/**
	 * 根据items 生成pdf
	 * 
	 * ---生成需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value ="/togenpdf/{id}" , method = RequestMethod.GET) 
	public String togenpdf(@PathVariable("id") Long itemsid, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes ) {
		
		Items items =  null ;
		String  itemscontent =  "" ;
		Boolean needwirteFileFlag = true; //true  不存在文件 需要写入文件 ; false 不需要写
		int existFile = 1 ; //已经存在文件了 
		
		items = itemsService.getItems(itemsid)  ; 
		
		if(items == null ){
			model.addAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();
			Long itemsId =  items.getId() ;
			ReturnResponse rr = iaccessauthService.checkItemsUserRead(CurrentUserId, itemsId);
			int retCode = rr.getRetCode() ;
			String retInfo  =rr.getRetInfo();
			if(retCode == IaccessauthService.rCanDo){
				itemscontent=items.getContents();
				if (items.getNeedfile() == existFile ) {
					needwirteFileFlag = false ;
				}
				model.addAttribute("items", items);			
			}else{
				model.addAttribute("message", retInfo);	
			}
		}
		
		/**
		 * 写pdf文件 
		 * 
		 * */		
		if("".equals(itemscontent) || itemscontent == null  ){
			String messagefail = "遇到系统错误 ,错误码01201311";
			model.addAttribute("messagefail", messagefail);
			return "redirect:/items/";
			
		}else if( !needwirteFileFlag){
			//已有文件, 不需要写入了 
			String messagesucc = "找到相应pdf";
			model.addAttribute("pdfFilename", items.getFilename());	
			model.addAttribute("messagesucc", messagesucc);	
			
			
			
			return "items/topdfPage";
		}else{
			//之前没有文件, 需要写入			
			String pdfFilename = UUID.randomUUID().toString();
			System.out.println("文件名称:"+pdfFilename);		
			String ctx = request.getContextPath();
			String realPath = request.getRealPath("/")+"static"+File.separator+"temphtml"+File.separator;
			//生成对应的html文件
			int begenokhtml = GenItemsHtml.GenHtmlForItem(pdfFilename, ctx, realPath, itemscontent);
			if ( begenokhtml == 1) {
			
				String filename2 =  realPath+pdfFilename+".pdf" ;				
				//url完整链接获取 获取网络的地址
				String urlpathpart = request.getContextPath();  
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+urlpathpart+"/"; 			
				String pageurl = basePath+"/static/temphtml/"+pdfFilename+".html ";			
				String messagesucc = "";
				String messagefail = "";
				if( HtmlToPdf.convert(pageurl,filename2) ){
					messagesucc = "成功生成pdf文件";
					
					//生成了 html 和 pdf 双重文件				
					items.setNeedfile(existFile);
					items.setFilename(pdfFilename);
					itemsService.saveItems(items);					
					
					model.addAttribute("messagesucc", messagesucc);
					model.addAttribute("pdfFilename", pdfFilename);
					//response.sendRedirect(request.getContextPath() + "/tmp/" + pdfName); 
				}else{
					messagefail = "文件生成失败 ，错误码 0120 1338"; //html文件生成ok pdf生成初选问题 
					model.addAttribute("messagefail", messagefail);
				}				
				return "items/topdfPage";
			}else{
				//html生成错误了  对应错误码 --- 错误码 01201115
				String messagefail =  "文件生成失败  错误码 01201115";;
				model.addAttribute("messagefail", messagefail);
				return "redirect:/items/";
			}
			
		}
		
		
	
	}
	
	/**
	 * 单个随机题目单的更新
	 * 
	 * ---更新需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value ="/toupdate/{id}" , method = RequestMethod.GET) 
	public String toupdateForm(@PathVariable("id") Long itemsid, Model model) {
		System.out.println("toupdate------"+itemsid);	
		Items items =  null ;
		items = itemsService.getItems(itemsid)  ; 
		if(items == null ){
			model.addAttribute("message", "空");
		}else{
			
			Long CurrentUserId = getCurrentUserId();			 
			ReturnResponse rr = iaccessauthService.checkItemsUserUpdate(CurrentUserId, items) ;
			int  retCode = rr.getRetCode();
			System.out.println("retCode------"+retCode);	
			if (retCode == iaccessauthService.rCanDo ){
				model.addAttribute("items", items);
			}else{
				model.addAttribute("message", rr.getRetInfo());
			}
		}
		
	    return "items/itemsUpdate";  
	}
	
	
	/**
	 * 题目删除
	 * 
	 * ---删除鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long itemsid,  Model model ,RedirectAttributes redirectAttributes) {
		
		Items items =  null ;
		items = itemsService.getItems(itemsid)  ; 
		if(items == null ){			
			redirectAttributes.addFlashAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();			 
			ReturnResponse rr =  iaccessauthService.checkItemsUserDel(CurrentUserId, items);
			if (rr.getRetCode() ==  iaccessauthService.rCanDo){
				//权限够 可删除
				itemsService.deleteItems(itemsid);				
			} 
			redirectAttributes.addFlashAttribute("message",rr.getRetInfo());
			
		}
		return "redirect:/items/";
	}
	
	
	/**
	 * 单个题目查看
	 * 
	 * ---查看需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value = "/showdetail/{id}" , method = RequestMethod.GET) 
	public String showdetail(@PathVariable("id") Long itemsid, Model model) {
		Items items =  null ;
		items = itemsService.getItems(itemsid)  ; 
		if(items == null ){
			model.addAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();
			Long itemsId =  items.getId() ;
			ReturnResponse rr = iaccessauthService.checkItemsUserRead(CurrentUserId, itemsId);
			int retCode = rr.getRetCode() ;
			String retInfo  =rr.getRetInfo();
			if(retCode == IaccessauthService.rCanDo){
				model.addAttribute("items", items);			
			}else{
				model.addAttribute("message", retInfo);	
			}
		}
		
	    return "items/itemsDetail";  
	}
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
