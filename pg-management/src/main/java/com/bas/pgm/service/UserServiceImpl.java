package com.bas.pgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.UserDao;
import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;

@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	@Override
	public String savePerson(User user) {
		return userDao.savePerson(user);
	}

	@Override
	public User getUser(String phone, String password) {
		return userDao.getUser(phone, password);
	}

	@Override
	public GuestInfo getGuestInfo(String phone) {
		GuestInfo total = userDao.getTotalGuests(phone);
		GuestInfo present = userDao.getPresentGuests(phone);
		total.setPresentGuests(present.getPresentGuests());
		
		return total;
	}

	@Override
	public List<PersonInfo> getFeeDueInfo(String phone) {
		
		//List<PersonInfo> infos = userDao.getFeeDueInfo(phone);
		
		return userDao.getFeeDueInfo(phone);
	}

}
