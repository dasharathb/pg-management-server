package com.bas.pgm.dao;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;

public interface PayInGuestDao {
	public Guest generateGuestId();

	public void savePerson(Person person);
}
