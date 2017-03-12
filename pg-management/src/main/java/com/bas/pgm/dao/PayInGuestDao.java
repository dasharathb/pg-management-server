package com.bas.pgm.dao;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;

public interface PayInGuestDao {
	public Guest generateGuestId();

	public void savePerson(Person person);

	public void pushMethodGuest(String objectId, Person person);

	public HostelGuests getGuestInfo(String hostelNum, String guestId);
}
