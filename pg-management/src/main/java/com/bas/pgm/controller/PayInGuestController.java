package com.bas.pgm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.Reason;
import com.bas.pgm.service.PayInGuestService;
import com.bas.pgm.service.UserService;

@Controller
public class PayInGuestController{

	@Autowired
	PayInGuestService payInGuestService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/register/guest/{userName}", method = RequestMethod.OPTIONS)
	public @ResponseBody Person registerGuestOptions(@RequestBody Person person, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		return person;
	}
	
	@RequestMapping(value="/register/guest/{userName}", method = RequestMethod.POST)
	public @ResponseBody Person registerGuest(@RequestBody Person person, @PathVariable(value = "userName") String hostelNum, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		String guestId = payInGuestService.savePerson(person, hostelNum);
		person.setGuestId(guestId);
		return person;
	}
	
	@RequestMapping(value="/guest/id", method=RequestMethod.POST)
	public @ResponseBody Guest getGuestId(@RequestBody Guest guest){
		
		return payInGuestService.generateGuestId();
	}

	@RequestMapping(value="/api/guests/{userName}", method=RequestMethod.GET)
	public @ResponseBody HostelGuests getGuests(@PathVariable(value = "userName") String hostelNum, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		HostelGuests guests = payInGuestService.getAllGuests(hostelNum);
		return guests;
	}
	
	@RequestMapping(value="/api/guest/info/{userPhone}/{guestId}", method=RequestMethod.GET)
	public @ResponseBody Person getGuestInfo(@PathVariable(value = "userPhone") String hostelNum, @PathVariable(value = "guestId") String guestId, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		Person guests = payInGuestService.getGuestInfo(hostelNum, guestId);
		return guests;
	}
	
	@RequestMapping(value="/api/fee/paid/{phone}/{guestId}/{amount}", method=RequestMethod.GET)
	public @ResponseBody List<PersonInfo> updateGuestFeePaid( @PathVariable(value = "phone") String phone, @PathVariable(value = "guestId") String guestId, @PathVariable(value = "amount") Integer amount, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		payInGuestService.updateFeePaidDtls(phone, guestId, amount);
		List<PersonInfo> result = userService.getFeeDueInfo(phone);
		return result;		
	}
	
	@RequestMapping(value="/api/in/out/{phone}/{guestId}", method=RequestMethod.OPTIONS)
	public @ResponseBody Reason updateGuestInOutInfoOps(@RequestBody Reason reason, @PathVariable(value = "phone") String phone, @PathVariable(value = "guestId") String guestId, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		//List<PersonInfo> result = userService.getFeeDueInfo(phone);
		return reason;		
	}
	
	@RequestMapping(value="/api/in/out/{phone}/{guestId}", method=RequestMethod.PUT)
	public @ResponseBody Reason updateGuestInOutInfo(@RequestBody Reason reason, @PathVariable(value = "phone") String phone, @PathVariable(value = "guestId") String guestId, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		payInGuestService.updateGuestInOutInfo(phone, guestId, reason);
		
		return reason;		
	}
	
	@RequestMapping(value="/api/guests/search/{userName}/{name}", method=RequestMethod.GET)
	public @ResponseBody HostelGuests getGuestSearch(@PathVariable(value = "userName") String hostelNum, @PathVariable(value = "name") String name, HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		HostelGuests guests = null;
		if(name != null && !name.equals("")){
			guests = payInGuestService.getSearchGuests(hostelNum, name);
		}else {
			guests = payInGuestService.getAllGuests(hostelNum);
		}
		return guests;
	}
}
