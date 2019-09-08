package com.ischoolbar.programmer.service;

import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.User;


public interface UserService {
    public User findByUsername(String username);
		
	}
    

