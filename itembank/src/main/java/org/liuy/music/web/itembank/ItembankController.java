package org.liuy.music.web.itembank;

import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.liuy.music.entity.Itembank;
import org.liuy.music.entity.Task;
import org.liuy.music.service.Itembank.ItembankService;
import org.liuy.music.service.account.ShiroDbRealm.ShiroUser;
import org.liuy.music.service.task.TaskService;
import org.springside.modules.web.Servlets;
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
		Long userId = getCurrentUserId();
		Itembank  itembank = new Itembank();
		itembank.setContent(uecontent);
		itembank.setDescription(description);
		itembank.setTitle("title");
		itembank.setUserId(userId);
		itembankService.saveItembank(itembank);
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
	

	
	@RequestMapping(value = "/showdetail/{id}" , method = RequestMethod.GET) 
	public String showdetail(@PathVariable("id") Long itembankid, Model model) {
		Itembank itembank =  null ;
		itembank = itembankService.getItembank(itembankid)  ; 
		if(itembank == null ){
			model.addAttribute("message", "空");
		}else{
			Long useId = getCurrentUserId();
			Long itembankuseId = itembank.getUserId() ;
			if(useId == itembankuseId){   //比对所拥有的用户是否 为现用户
				model.addAttribute("itembank", itembank);
			}else{
				model.addAttribute("message", "无权查看此题目");
			}
		}
		
	    return "itembank/itembankDetail";  
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
//	@RequestMapping(value = "delete/{id}")
//	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
//		taskService.deleteTask(id);
//		redirectAttributes.addFlashAttribute("message", "删除任务成功");
//		return "redirect:/task/";
//	}

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
}
