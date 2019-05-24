package com.shenzk.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.shenzk.service.UserService;

@Component
@Service(interfaceClass=UserService.class)
public class UserServiceImpl implements UserService {

	@Override
	public String insert() {
		return "dubbo test success";
	}

}
