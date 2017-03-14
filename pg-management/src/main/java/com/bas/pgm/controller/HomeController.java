package com.bas.pgm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.pgm.model.User;
import com.bas.pgm.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/ent", method=RequestMethod.GET)
	public @ResponseBody Object entryPoint(){
		
		return "Hello this is entry point...";		
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.OPTIONS)
	public @ResponseBody User registerUser1(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		System.out.println(user.toString());
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		return user;		
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public @ResponseBody User registerUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		System.out.println(user.toString());
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		String result = userService.savePerson(user);
		
		return user;		
	}
}
