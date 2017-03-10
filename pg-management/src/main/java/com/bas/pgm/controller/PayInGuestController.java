package com.bas.pgm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.pgm.model.Country;
import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;
import com.bas.pgm.service.PayInGuestService;

@Controller
public class PayInGuestController{

	@Autowired
	PayInGuestService payInGuestService;
	
	@RequestMapping(value="/register/guest", method = RequestMethod.OPTIONS)
	public @ResponseBody Person registerGuestOptions(@RequestBody Person person, HttpServletRequest request, HttpServletResponse response){
		
		return person;
	}
	
	@RequestMapping(value="/register/guest", method = RequestMethod.POST)
	public @ResponseBody Person registerGuest(@RequestBody Person person, HttpServletRequest request, HttpServletResponse response){
		System.out.println(person.toString());
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		String guestId = payInGuestService.savePerson(person);
		person.setId(guestId);
		return person;
	}
	
	@RequestMapping(value="/guest/id", method=RequestMethod.POST)
	public @ResponseBody Guest getGuestId(@RequestBody Guest guest){
		
		return payInGuestService.generateGuestId();
	}
	
	//@RequestMapping(value = "/countries", method=org.springframework.web.bind.annotation.RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Country addCountry(@RequestBody Country country, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		return  country;
	}
}
