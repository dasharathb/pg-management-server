package com.bas.pgm.service;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;

public interface PayInGuestService {
	public Guest generateGuestId();

	public String savePerson(Person person);
}
