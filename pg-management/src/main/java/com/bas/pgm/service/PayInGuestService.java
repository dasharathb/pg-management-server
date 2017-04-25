package com.bas.pgm.service;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.Reason;

public interface PayInGuestService {
	public Guest generateGuestId();

	public String savePerson(Person person, String hostelNum);

	public HostelGuests getAllGuests(String hostelNum);

	public Person getGuestInfo(String hostelNum, String guestId);

	public void updateFeePaidDtls(String phone, String guestId, String amount);

	public void updateGuestInOutInfo(String phone, String guestId, Reason reason);
}
