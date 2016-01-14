package org.liuy.music.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.liuy.music.entity.Itembankkind;
import org.liuy.music.entity.Itembanklevel;
import org.liuy.music.entity.Itembankrange;
import org.liuy.music.service.Itembank.ItembankService;
import org.liuy.music.service.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.baidu.ueditor.ActionEnter;

/**
 * Itembank 的 Restful API的Controller. 
 * 	REAL 与ueditor区分
 * 
 * @author liuy
 */
@RestController
@RequestMapping(value = "/itembankrest")
public class ItembankrealRestController {
	private static Logger logger = LoggerFactory.getLogger(ItembankrealRestController.class);

	@Autowired
	private ItembankService itembankService;
	
	/**
	 * 获取 itembank 的分类 （Itembankclzss）
	 *  
	 * http://localhost:8080/itembank/itembankrest/gogetitembankkinds
	 * 
	 * */
	@RequestMapping(value ="/gogetitembankkinds" ,produces = MediaTypes.JSON_UTF_8)	 
    public List <Itembankkind> gogetitembankkinds(HttpServletRequest request,  HttpServletResponse response) {
		
		List <Itembankkind> itembankkinds= itembankService.gogetAllItembankkind() ;
		return itembankkinds;
		
	}
	
	/**
	 * 获取 itembank 的等级 （Itembanklevel）
	 *  
	 * http://localhost:8080/itembank/itembankrest/gogetitembanklevel
	 * 
	 * */
	@RequestMapping(value ="/gogetitembanklevel" ,produces = MediaTypes.JSON_UTF_8)	 
    public List <Itembanklevel> gogetitembanklevel(HttpServletRequest request,  HttpServletResponse response) {
		
		List <Itembanklevel> itembanklevel= itembankService.gogetAllItembanklevel() ;
		return itembanklevel;
		
	}
	
	
	/**
	 * 获取 itembankrange 范围 知识点 （itembankrange）
	 *  
	 * http://localhost:8080/itembank/itembankrest/gogetitembankranges
	 * 
	 * */
	@RequestMapping(value ="/gogetitembankranges" ,produces = MediaTypes.JSON_UTF_8)	 
    public List <Itembankrange> gogetitembankranges(HttpServletRequest request,  HttpServletResponse response) {
		
		List <Itembankrange> itembankranges= itembankService.gogetAllItembankrange() ;
		return itembankranges;
		
	}


	public ItembankService getItembankService() {
		return itembankService;
	}
	public void setItembankService(ItembankService itembankService) {
		this.itembankService = itembankService;
	}
	
	
	
	
	
}
