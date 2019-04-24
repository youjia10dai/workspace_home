package com.qfjy.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfjy.bean.Users;
import com.qfjy.service.UsersService;

@Controller
@RequestMapping("/user")
public class cotroller {

	@Autowired
	private UsersService service;
	
	@RequestMapping("/all")
	@ResponseBody
	public List<Users> findAll(){
		System.out.println("接受到请求");
		return service.findAll();
	}
	
}
