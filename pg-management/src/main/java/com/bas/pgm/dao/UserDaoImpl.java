package com.bas.pgm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.bas.pgm.model.User;

@Component(value="userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public String savePerson(User user) {
		mongoTemplate.save(user);
		return "user created";
	}

}
