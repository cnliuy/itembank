package org.liuy.music.web.itembank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.ReturnResponse;
import org.liuy.music.entity.Task;
import org.liuy.music.repository.ItembankDao;
import org.liuy.music.service.Itembank.IaccessauthService;
import org.liuy.music.service.Itembank.ItembankService;
import org.liuy.music.service.account.ShiroDbRealm.ShiroUser;
import org.liuy.music.service.task.TaskService;
import org.liuy.music.tools.CheckString;
import org.liuy.music.tools.HtmlToPdf;
import org.springside.modules.web.Servlets;
import org.webbitserver.handler.StaticFile;

import com.google.common.collect.Maps;


@Controller
@RequestMapping(value = "/itembank")
public class ItembankController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		//sortTypes.put("title", "标题");
	}

	@Autowired
	private ItembankService itembankService;
	
	@Autowired
	private IaccessauthService iaccessauthService;


	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();
		Page<Itembank> itembanks = itembankService.getUserItembankList(userId, pageNumber, pageSize, sortType);
		model.addAttribute("itembanks", itembanks);
		model.addAttribute("sortType", sortType);
		//model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		//model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "itembank/itembankList";
	}

	@RequestMapping(value = "tocreateitembankpage", method = RequestMethod.GET)
	public String createForm(Model model) {		 
		model.addAttribute("action", "create");
		return "itembank/itembankForm";
	}
	
	@RequestMapping(value = "tocreateitembankListpage", method = RequestMethod.GET)
	public String tocreateitembankListpage(Model model) {		 
		model.addAttribute("action", "create");
		return "itembank/itembankgenList";
	}
	
	
	/**
	 * 生成pdf文件
	 * */
	@RequestMapping(value = "genPdfFile" )
	public String genPdfFile(Model model ,HttpServletRequest request ) {	
		String pdfFilename = request.getParameter("pdfFilename");
		pdfFilename = pdfFilename.replace(".html", "");
		System.out.println("文件名称:"+pdfFilename);
		
		 
		String realPath = request.getRealPath("/")+"static"+File.separator+"temphtml"+File.separator;
		String filename =  realPath+pdfFilename+".pdf" ;
		//url完整链接获取
		String urlpathpart = request.getContextPath();  
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+urlpathpart+"/";  
		String pageurl = basePath+"/static/temphtml/"+pdfFilename+".html ";
		
		
		String messagesucc = "";
		String messagefail = "";
		if( HtmlToPdf.convert(pageurl,filename) ){
			messagesucc = "成功生成pdf文件";
			model.addAttribute("messagesucc", messagesucc);
			model.addAttribute("pdfFilename", pdfFilename);
			//response.sendRedirect(request.getContextPath() + "/tmp/" + pdfName); 
		}else{
			messagefail = "pdf文件生成失败";
			model.addAttribute("messagefail", messagefail);
		}
		
		return "itembank/topdfPage";
	}
	

	
	/**
	 * 根据范围和数目 生成题目列表
	 * */
	@RequestMapping(value = "genitembankList" )
	public String genitembankList(Model model ,HttpServletRequest request ) {		 
		//model.addAttribute("action", "create");		
		String itemnum = request.getParameter("itemnum").trim();
		
		String itemrange1 = request.getParameter("itemrange1").trim();
		//if ( CheckString.cclm(itemrange1)){
		//if (itemrange1.substring(0, 1).equals("?") ){
			//itemrange1= itemrange1.trim().substring(1);	
		//}		
		
		String itemrange2 = request.getParameter("itemrange2").trim();	
		//System.out.println(  CheckString.cclm(itemrange2) );
		//if ( CheckString.cclm(itemrange2)){
		//if (itemrange2.substring(0, 1).equals("?") ){
		//	itemrange2= itemrange2.trim().substring(1);	
		//}
		
		String itemclassify = request.getParameter("itemclassify").trim();
		//if ( CheckString.cclm(itemclassify)){
		//if (itemclassify.substring(0, 1).equals("?") ){
		//	itemclassify= itemclassify.trim().substring(1);
		//}
		//}
		model.addAttribute("itemrange1", itemrange1);
		model.addAttribute("itemrange2", itemrange2);
		model.addAttribute("itemclassify", itemclassify);
		model.addAttribute("itemnum", itemnum);
		
		//出现怪异字符 需要删除 第一个？字符  上面使用 .substring(1);------处理前
		// <option value = 加入value属性 不存在这个 ?的问题了    ------处理后
		//----------------- 音的性质-------- 简易----------- 填空题
		System.out.println("------------"+itemnum+"-----"+itemrange1+"--------"+itemrange2+"-----------"+itemclassify );
		
		// 需要调整  liuy
		Integer iitemnum =-1 ;
		int  flag = 1 ;		 
		try {
			Integer.parseInt(itemnum) ;
		} catch (Exception e) {
			flag = 0 ;
		}
		
		if (flag == 1 ){//无异常
			iitemnum = Integer.parseInt(itemnum) ; 
		}
		
		if (iitemnum == -1 ){
			//题目数异常
		}else{
			//正常
			//查找题目，随机生成题目列表
			//itemrange1
			//itemclassify
			//itemrange2
			List<Itembank> ibs =itembankService.gogetItembankList(getCurrentUserId(), itemclassify, itemrange1, itemrange2);
			Iterator ibsi = ibs.iterator() ;
			System.out.println( "----------------") ;
			while(ibsi.hasNext()){
				Itembank i = (Itembank)ibsi.next();
				System.out.println( i.getId()) ;
			}
			System.out.println("生成的题目数："+iitemnum);
		}
		
		
		//--实验--
		List<Long> itembankIds = new ArrayList();
		itembankIds.add(1L);
		itembankIds.add(3L);
		List<Itembank>  ibs = itembankService.gogetUserItembankList(itembankIds);
		if(ibs.size() == 0 || ibs == null){
			model.addAttribute("message", "题目生成出现错误");
		}else{
			
			model.addAttribute("itembanks", ibs);
			
			String htmlbodystr = "";
			String htmlbodyanswerstr = "";
			Iterator <Itembank> ibsi = ibs.iterator() ;
			int tmpi = 0 ;
			while(ibsi.hasNext()){
				Itembank ibooo = ibsi.next() ;
				tmpi++;
				htmlbodystr = htmlbodystr+tmpi+"."+ibooo.getContent() +"<br> \r\n";
				htmlbodyanswerstr = htmlbodyanswerstr+tmpi+"."+ibooo.getItemanswer()+"\r\n" ;
			}			 
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
		    + htmlbodystr+"<br></br>"+"答案是：<br>"+htmlbodyanswerstr
			+ "	</table>\r\n"
			+ "</body>\r\n"
			+ "</html>"	;
			
			String realPath = request.getRealPath("/")+"static"+File.separator+"temphtml"+File.separator;
			
			//System.out.println(htmlstr);
			System.out.println("realPath:"+realPath);
			String message = "题目生成成功" ;
			
			String filename ="";
			try{
				
				String s1 = UUID.randomUUID().toString();
				//String s2 = UUID.randomUUID().toString();
				filename = s1+".html";
				//File file = new File(realPath+filename);
				// if file doesnt exists, then create it
				//if (!file.exists()) {
				//	file.createNewFile();
				//}
				//FileWriter fw = new FileWriter(file.getAbsoluteFile());
				//BufferedWriter bw = new BufferedWriter(fw);
				//bw.write(htmlstr);
				//bw.close();
			   
				FileOutputStream fos = new FileOutputStream(realPath+filename); 
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
				osw.write(htmlstr); 
				osw.flush(); 
				osw.close();
				
				
				model.addAttribute("filename", filename);
				
				message = message + " html页面生成成功 " ;				
				System.out.println("生成文件成功 ");
			} catch (IOException e) {
				e.printStackTrace();
			}			
			model.addAttribute("message", message);
			
			 
		}
		
		return "itembank/itembankgenList";
	}

//	@RequestMapping(value = "create", method = RequestMethod.POST)
//	public String create( HttpServletRequest request,  HttpServletResponse response) {
//		User user = new User(getCurrentUserId());
//		newTask.setUser(user);
//		taskService.saveTask(newTask);
//		return "redirect:/task/";
//	}
	
	
	/**
	 * 方法2.
	 * 
	 * http://localhost:8080/itembank/postuecontent
	 * 通过Post提交上来的参数 ，在表单提交后，通过js修改提交的数据。
	 * 	---对应 taskForm2.jsp 页面中的表单提交后，修改表单中属性值的 js代码
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
	 * ---新建无
	 *    需鉴权
	 * 
	 * */
	@RequestMapping("/postuecontent")
    public String postuecontent(HttpServletRequest request,  HttpServletResponse response) {
		//System.out.println("in  itembankController() postuecontent");
		//String uecontent =	(String) request.getAttribute("uepostcontent");   
		//System.out.println("uecontent:"+uecontent); //空值
		String uecontent = request.getParameter("uepostcontent");
		//System.out.println("uecontent2:"+uecontent);		
		String itemclassify = request.getParameter("itemclassify");
		String description = request.getParameter("description");
		String itemanswer = request.getParameter("itemanswer");
		String itemrange1 = request.getParameter("itemrange1");
		String itemrange2 = request.getParameter("itemrange2");
		System.out.println("itemrange2------"+itemrange2);
		
		
		Long userId = getCurrentUserId();
		Itembank  itembank = new Itembank();
		itembank.setContent(uecontent);
		itembank.setDescription(description);
		itembank.setItemclassify(itemclassify);
		itembank.setTitle("title");
		itembank.setItemanswer(itemanswer);
		itembank.setItemrange1(itemrange1);
		itembank.setItemrange2(itemrange2);
		itembank.setUserId(userId);
		itembankService.saveItembank(itembank);
		return "redirect:/itembank/";
	}
	
	/**
	 * 更新内容 
	 * 
	 *  ---修改需鉴权
	 * 
	 * */
	@RequestMapping("/updatepostuecontent")
    public String updatepostuecontent(HttpServletRequest request,  HttpServletResponse response , Model model ,RedirectAttributes redirectAttributes) {
		//System.out.println("in  itembankController() postuecontent");
		//String uecontent =	(String) request.getAttribute("uepostcontent");   
		//System.out.println("uecontent:"+uecontent); //空值
		String uecontent = request.getParameter("uepostcontent");
		String itembankId = request.getParameter("itembankId");
		String itemanswer = request.getParameter("itemanswer");
		String itemrange1 = request.getParameter("itemrange1");
		String itemrange2 = request.getParameter("itemrange2");
		
		System.out.println("uecontent2:"+uecontent);		
		String itemclassify = request.getParameter("itemclassify");
		String description = request.getParameter("description");
		Long userId = getCurrentUserId();
		
		Itembank  itembank = null;
		if(itembankId == null || "".equals(itembankId)) {
			//model.addAttribute("message", "不存在此题目");
			redirectAttributes.addFlashAttribute("message", "不存在此题目");
		}else{
			try {
				Long itembankIdl= Long.parseLong(itembankId); 
				itembank = itembankService.getItembank(itembankIdl);			 
				Long CurrentUserId = getCurrentUserId();
				ReturnResponse rr = iaccessauthService.checkUserUpdate(CurrentUserId, itembankId);
				int retCode = rr.getRetCode() ;
				String retInfo  =rr.getRetInfo();
				if(retCode == IaccessauthService.rCanDo){
					itembank.setContent(uecontent);
					itembank.setDescription(description);
					itembank.setItemclassify(itemclassify);
					itembank.setTitle("title");
					itembank.setItemanswer(itemanswer);
					itembank.setItemrange1(itemrange1);
					itembank.setItemrange2(itemrange2);
				 	itembankService.saveItembank(itembank);				
				}
				redirectAttributes.addFlashAttribute("message", retInfo);
			} catch (Exception e) {			
				redirectAttributes.addFlashAttribute("message", "参数错误，String到Long转化失败");		
			}
			
		}
		

		return "redirect:/itembank/";
	}
	
	
	
	
	@RequestMapping(value = "cccc" )
	@ResponseBody 
	public Map<String, String> create2(@RequestParam(value = "htmlinfo") String htmlinfo ) {
		 
		System.out.println("---------------htmlinfo2:"+htmlinfo);
		Map<String, String> map = new HashMap<String, String>();  
	    map.put("success", "true");  
	    return map;  
	}
	

	/**
	 * 单个题目查看
	 * 
	 * ---查看需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value = "/showdetail/{id}" , method = RequestMethod.GET) 
	public String showdetail(@PathVariable("id") Long itembankid, Model model) {
		Itembank itembank =  null ;
		itembank = itembankService.getItembank(itembankid)  ; 
		if(itembank == null ){
			model.addAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();
			Long itembankId =  itembank.getId() ;
			ReturnResponse rr = iaccessauthService.checkUserRead(CurrentUserId, itembankId);
			int retCode = rr.getRetCode() ;
			String retInfo  =rr.getRetInfo();
			if(retCode == IaccessauthService.rCanDo){
				model.addAttribute("itembank", itembank);			
			}else{
				model.addAttribute("message", retInfo);	
			}
		}
		
	    return "itembank/itembankDetail";  
	}
	
	/**
	 * 单个题目更新
	 * 
	 * ---更新需鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value ="/toupdate/{id}" , method = RequestMethod.GET) 
	public String toupdateForm(@PathVariable("id") Long itembankid, Model model) {
		//System.out.println("here toupdate ");
		Itembank itembank =  null ;
		itembank = itembankService.getItembank(itembankid)  ; 
		if(itembank == null ){
			model.addAttribute("message", "空");
		}else{
			
			Long CurrentUserId = getCurrentUserId();			 
			ReturnResponse rr = iaccessauthService.checkUserUpdate(CurrentUserId, itembank) ;
			int  retCode = rr.getRetCode();
			if (retCode == iaccessauthService.rCanDo ){
				model.addAttribute("itembank", itembank);
			}else{
				model.addAttribute("message", rr.getRetInfo());
			}
		}
		
	    return "itembank/itembankUpadate";  
	}
	
	/**
	 * 题目删除
	 * 
	 * ---删除鉴权
	 * 
	 * 
	 * */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long itembankid,  Model model ,RedirectAttributes redirectAttributes) {
		
		Itembank itembank =  null ;
		itembank = itembankService.getItembank(itembankid)  ; 
		if(itembank == null ){			
			redirectAttributes.addFlashAttribute("message", "空");
		}else{
			Long CurrentUserId = getCurrentUserId();			 
			ReturnResponse rr =  iaccessauthService.checkUserDel(CurrentUserId, itembank);
			if (rr.getRetCode() ==  iaccessauthService.rCanDo){
				//权限够 可删除
				itembankService.deleteItembank(itembankid);				
			} 
			redirectAttributes.addFlashAttribute("message",rr.getRetInfo());
			
		}
		return "redirect:/itembank/";
	}


//	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
//	public String updateForm(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("task", taskService.getTask(id));
//		model.addAttribute("action", "update");
//		return "task/taskForm";
//	}
//
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	public String update(@Valid @ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
//		taskService.saveTask(task);
//		redirectAttributes.addFlashAttribute("message", "更新任务成功");
//		return "redirect:/task/";
//	}
//

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
//	@ModelAttribute
//	public void getTask(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
//		if (id != -1) {
//			model.addAttribute("task", taskService.getTask(id));
//		}
//	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	
	

	public IaccessauthService getIaccessauthService() {
		return iaccessauthService;
	}

	public void setIaccessauthService(IaccessauthService iaccessauthService) {
		this.iaccessauthService = iaccessauthService;
	}
}
