package com.bas.pgm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.pgm.model.Guest;
import com.bas.pgm.service.PayInGuestService;

@Controller
public class PayInGuestController{

	@Autowired
	PayInGuestService payInGuestService;
	
	@RequestMapping(value="/register/guest", method=RequestMethod.POST)
	public @ResponseBody Object registerGuest(){
		
		return null;
	}
	
	@RequestMapping(value="/guest/id", method=RequestMethod.GET)
	public @ResponseBody Guest getGuestId(){
		
		return payInGuestService.generateGuestId();
	}
}
