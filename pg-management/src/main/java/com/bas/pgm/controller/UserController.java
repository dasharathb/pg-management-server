package com.bas.pgm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping(value="/register/guest", method=RequestMethod.POST)
	public @ResponseBody Object registerGuest(){
		
		return null;
	}
	
	private void generateGuestId(){
		
	}
}
