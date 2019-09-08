package com.ischoolbar.programmer.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;
import com.ischoolbar.programmer.util.CpachaUtil;
import com.mysql.jdbc.Buffer;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * system controller
 * @author saxue
 *
 */
@RequestMapping("/system")
@Controller

public class SystemController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
     public ModelAndView index(ModelAndView model) {
		model.setViewName("system/index");
		
    	 return model;
     }
	/**

	 * login interface
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		model.addObject("user","sam");
   	 return model;
    }
	
	/**
	 * post form
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
    public Map<String, String> login(
    		@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password",required=true) String password,
			@RequestParam(value="vcode",required=true) String vcode,
			@RequestParam(value="type",required=true) Integer type,

			HttpServletRequest request) {
		
	  Map<String, String> ret=new HashMap<>();
	  if(StringUtils.isEmpty(username)) {
		  ret.put("type", "error");
		  ret.put("msg", "username can not be empty!");
		  return ret;
	  }
	  if(StringUtils.isEmpty(password)) {
		  ret.put("type", "error");
		  ret.put("msg", "password can not be empty!");
		  return ret;
	  }
	  if(StringUtils.isEmpty(vcode)) {
		  ret.put("type", "error");
		  ret.put("msg", "vcode can not be empty!");
		  return ret;
	  }
	  String loginCpacha= (String)request.getSession().getAttribute("loginCpacha");
	  if(StringUtils.isEmpty(loginCpacha)) {
		  ret.put("type", "error");
		  ret.put("msg", "session is invalid,pleast refresh!");
		  return ret;
	  }
	  if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
		  ret.put("type", "error");
		  ret.put("msg", "verify code is wrong!");
		  return ret;
	  }
	  request.getSession().setAttribute("loginCapha",null);
	  //get user from database
	  
	  // search user from database
	  if(type==1) {
		  //admin
		  User user=userService.findByUsername(username);
		  if (user==null) {
			  ret.put("type", "error");
			  ret.put("msg", "user is not exist!");
			  return ret;
		  }
		  if(!password.equals(user.getPassword())) {
			  ret.put("type", "error");
			  ret.put("msg", "wrong password!");
			  return ret;
		  }
		  request.getSession().setAttribute("user", user); 
	  }else {//student
		  
		  
	  }
	  
	  ret.put("type", "success");
	  ret.put("msg", "log successfully!");
	  
   	 return ret;
    }
	
	/**
	 * verify code
	 * @param request
	 * @param vl
	 * @param w
	 * @param h
	 * @param response
	 */
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,
			HttpServletResponse response) {
		CpachaUtil cpachaUtil= new CpachaUtil(vl,w,h);
		String generatorVode=cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha",generatorVode);
		BufferedImage generatorRotateVCodeImage= cpachaUtil.generatorRotateVCodeImage(generatorVode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	
}
