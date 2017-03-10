package com.bas.pgm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;

@Component(value="payInGuestDao")
public class PayInGuestDaoImpl implements PayInGuestDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public Guest generateGuestId() {
		Guest guest = mongoTemplate.findById(1, Guest.class);
		if(guest == null){
			mongoTemplate.save(new Guest(1, 001));
			return new Guest(1, 001);
		}else{
			mongoTemplate.save(new Guest(1, guest.getGuestId()+1));		
		}
		return guest;
	}

	@Override
	public void savePerson(Person person) {
		
		mongoTemplate.save(person);
	}

}
