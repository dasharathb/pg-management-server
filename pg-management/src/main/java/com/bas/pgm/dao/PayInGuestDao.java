package com.bas.pgm.dao;

import java.util.Date;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.Reason;

public interface PayInGuestDao {
	public Guest generateGuestId();

	//public void savePerson(Person person);

	public void pushMethodGuest(String objectId, Person person);

	public Person getGuestInfo(String hostelNum, String guestId);

	public void updateFeePaidDtls(String phone, String guestId, Integer amount, Date feeDueDate);

	void updateGuestInOutInfo(String phone, String guestId, Reason reason);
}
