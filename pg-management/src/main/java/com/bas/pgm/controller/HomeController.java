package com.bas.pgm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;
import com.bas.pgm.service.UserService;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/ent", method=RequestMethod.GET)
	public @ResponseBody Object entryPoint(){
		logger.info("Hello this is ... /ent service");
		return "Hello this is entry point...";		
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.OPTIONS)
	public @ResponseBody User registerUser1(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		return user;		
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public @ResponseBody User registerUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		//String result = 
				userService.savePerson(user);
		
		return user;		
	}
	
	
	@RequestMapping(value="/get/user/{phone}/{password}/{deviceId}", method=RequestMethod.GET)
	public @ResponseBody User getUser( @PathVariable(value = "phone") String phone, @PathVariable(value = "password") String password, @PathVariable(value = "deviceId") String deviceId, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		User result = userService.getUser(phone, password);
		if(result != null){
			userService.updateUserDeviceId(phone, deviceId);
		}
		return result;		
	}
	
	@RequestMapping(value="/user/{deviceUUId}", method=RequestMethod.GET)
	public @ResponseBody User getUserwithDeviceId( @PathVariable(value = "deviceUUId") String deviceId, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		User result = userService.getUserWithDeviceId(deviceId);
		if(result == null){
			return new User();
		}
		return result;		
	}
	@RequestMapping(value="/api/guest/count/{phone}", method=RequestMethod.GET)
	public @ResponseBody GuestInfo getGuestInformation( @PathVariable(value = "phone") String phone, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		GuestInfo result = userService.getGuestInfo(phone);
		return result;		
	}
	@RequestMapping(value="/api/fee/due/{phone}", method=RequestMethod.GET)
	public @ResponseBody List<PersonInfo> getGuestFeeDueInformation( @PathVariable(value = "phone") String phone, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		List<PersonInfo> result = userService.getFeeDueInfo(phone);
		return result;		
	}
	
	@RequestMapping(value="/api/hostel/fee/{userPhone}/{hFee}", method=RequestMethod.GET)
	public @ResponseBody User updateHostelFee(@PathVariable(value = "userPhone") String hostelNum, @PathVariable(value = "hFee") String hFee, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		User user = userService.getUpdateHostelFee(hostelNum, hFee);
		
		return user;
	}
}
