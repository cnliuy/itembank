/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.liuy.music.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.http.HttpRequest;
import org.liuy.music.entity.Task;
import org.liuy.music.service.task.TaskService;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.baidu.ueditor.ActionEnter;

/**
 * Task的Restful API的Controller.
 * 
 * @author liuy
 */
@RestController
@RequestMapping(value = "/api/v1/task")
public class TaskRestController {

	private static Logger logger = LoggerFactory.getLogger(TaskRestController.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private Validator validator;

	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Task> list() {
		return taskService.getAllTask();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Task get(@PathVariable("id") Long id) {
		Task task = taskService.getTask(id);
		if (task == null) {
			String message = "任务不存在(id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return task;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody Task task, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, task);

		// 保存任务
		taskService.saveTask(task);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = task.getId();
		URI uri = uriBuilder.path("/api/v1/task/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	// 按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Task task) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, task);

		// 保存任务
		taskService.saveTask(task);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		taskService.deleteTask(id);
	}
	
	
	//http://localhost:8080/itembank/api/v1/task/gotaskcontroll
	@RequestMapping(value = "gotaskcontroll" )
	public String gotaskcontroller(ServletRequest req ,ServletResponse resp) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		System.out.println("----------- gotaskcontroller------------"+new Date());
		//model.addAttribute("task", new Task());
		//model.addAttribute("action", "gocreate");
		//return "task/gocreate";
		try {
			request.setCharacterEncoding( "utf-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Type" , "text/html");		
		//String rootPath = application.getRealPath( "/" );
		//String herePath = request.getSession().getServletContext().getRealPath("/");
		String herePath = UrlUtil.getClassFilePath(TaskRestController.class);
		herePath=herePath.replace("TaskRestController.class", "");
		System.out.println(herePath);
		String jsonfile = "";
		try {
			jsonfile = new ActionEnter( request, herePath ).exec();
			System.out.println(jsonfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//	out.write( new ActionEnter( request, rootPath ).exec() );
		
	    //return this.parentPath + File.separator + ConfigManager.configFileName;
        //I modify this, because it is not suit for our porject.

		return jsonfile;
	}
}
