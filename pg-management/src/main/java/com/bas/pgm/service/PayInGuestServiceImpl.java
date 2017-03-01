package com.bas.pgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.PayInGuestDao;
import com.bas.pgm.model.Guest;

@Service(value="payInGuestService")
public class PayInGuestServiceImpl implements PayInGuestService {

	@Autowired
	PayInGuestDao payInGuestDao;
	
	@Override
	public Guest generateGuestId() {
		
		return payInGuestDao.generateGuestId();
	}

	
}
