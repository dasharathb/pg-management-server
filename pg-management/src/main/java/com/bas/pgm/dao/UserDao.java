package com.bas.pgm.dao;

import java.util.List;

import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;

public interface UserDao {
	public String savePerson(User user);

	public User getUser(String phone, String password);

	public GuestInfo getTotalGuests(String phone);

	public GuestInfo getPresentGuests(String phone);

	public List<PersonInfo> getFeeDueInfo(String phone);

	public User getUserWithDeviceId(String deviceId);

	void updateMethod(String phone, String deviceId);
}
