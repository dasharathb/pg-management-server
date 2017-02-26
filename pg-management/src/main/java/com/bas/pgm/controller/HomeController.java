package com.bas.pgm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value="/ent", method=RequestMethod.GET)
	public @ResponseBody Object entryPoint(){
		
		return "Hello this is entry point...";		
	}
}
