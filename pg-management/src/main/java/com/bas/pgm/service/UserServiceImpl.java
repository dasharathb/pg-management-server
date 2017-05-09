package com.bas.pgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.UserDao;
import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;
import com.bas.pgm.mongo.repo.UserRepo;

@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public String savePerson(User user) {
		return userDao.savePerson(user);
	}

	@Override
	public User getUser(String phone, String password) {
		return userDao.getUser(phone, password);
	}

	@Override
	public GuestInfo getGuestInfo(String phone) {
		GuestInfo total = userDao.getTotalGuests(phone);
		GuestInfo present = userDao.getPresentGuests(phone);
		if(present != null)
			total.setPresentGuests(present.getPresentGuests());
		
		return total;
	}

	@Override
	public List<PersonInfo> getFeeDueInfo(String phone) {
		User user = userRepo.findByPhone(phone);
		List<PersonInfo> infos = userDao.getFeeDueInfo(phone);
		for(PersonInfo personInfo :  infos){
			personInfo.getGuests().setAmount(user.gethFee()+(personInfo.getGuests().getDueAmount() == null ? 0 : personInfo.getGuests().getDueAmount()));
		}
		return infos;
	}

	@Override
	public User getUserWithDeviceId(String deviceId) {
		
		return userDao.getUserWithDeviceId(deviceId);
	}

	@Override
	public void updateUserDeviceId(String phone, String deviceId) {
		userDao.updateMethod(phone, deviceId);
		
	}

}
