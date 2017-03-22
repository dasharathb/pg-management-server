package com.bas.pgm.service;

import java.util.List;

import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;

public interface UserService {
	public String savePerson(User user);

	public User getUser(String phone, String password);

	public GuestInfo getGuestInfo(String phone);

	public List<PersonInfo> getFeeDueInfo(String phone);
}
