package com.shenzk.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shenzk.service.UserService;

@Component
public class UserController implements CommandLineRunner{
	
	@Reference
	UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(userService.insert());
	}

}
