package com.ischoolbar.programmer.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;

/**
 * user(admin) controller
 * @author saxue
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	public UserService UserService;
     /**
      * user(admin) list
      * @param model
      * @return
      */
	
	
     @RequestMapping(value="/list",method=RequestMethod.GET)
     public ModelAndView list(ModelAndView model) {
    	 model.setViewName("user/user_list");
    	 
    	 return model;
     }
     @RequestMapping(value="/add",method=RequestMethod.POST)
     @ResponseBody
     //return json data typle
     public Map<String, String> add(User user){
    	 Map<String, String> ret=new HashMap<>();
    	 if(user==null) {
    	  ret.put("type", "error");
   		  ret.put("msg", "error!");
   		  return ret;
    	 }
    	 if(StringUtils.isEmpty(user.getPassword())) {
   		  ret.put("type", "error");
   		  ret.put("msg", "password can not be empty!");
   		  return ret;
   	  }
   	  if(StringUtils.isEmpty(user.getUsername())) {
   		  ret.put("type", "error");
   		  ret.put("msg", "username can not be empty!");
   		  return ret;
   	  }
    	 ret.put("type", "success");
  		  ret.put("msg", "add successfully!"); 
  		  return ret;
     }
}
