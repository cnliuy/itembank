/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.liuy.music.web.items;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		
		model.addAttribute("contents", contents);			
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
				if(retCode == IaccessauthService.rCanDo){
					items.setContents(uecontent);
					items.setTitle(title);
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
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
