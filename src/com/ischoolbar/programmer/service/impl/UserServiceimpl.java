package com.ischoolbar.programmer.service.impl;
import com.ischoolbar.programmer.dao.UserDao;
import com.ischoolbar.programmer.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.User;

@Service
public class UserServiceimpl implements UserService{
   @Autowired
   private UserDao userDao;
   
 

@Override
public User findByUsername(String username) {
	// TODO Auto-generated method stub
	return userDao.findByUserName(username);
}
}