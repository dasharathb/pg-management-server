package com.bas.pgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.UserDao;
import com.bas.pgm.model.User;

@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	@Override
	public String savePerson(User user) {
		return userDao.savePerson(user);
	}

}
