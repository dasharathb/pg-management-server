package com.bas.pgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.PayInGuestDao;
import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;

@Service(value="payInGuestService")
public class PayInGuestServiceImpl implements PayInGuestService {

	@Autowired
	PayInGuestDao payInGuestDao;
	
	@Override
	public Guest generateGuestId() {
		
		return payInGuestDao.generateGuestId();
	}

	@Override
	public String savePerson(Person person) {
		Guest guest = generateGuestId();
		String[] name = person.getName().split(" ");
		String guestId = name[0].charAt(0)+name[1].charAt(0)+""+guest.getGuestId();
		person.setId(guestId);
		payInGuestDao.savePerson(person);
		return guestId;
	}

	
}
