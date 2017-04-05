package com.bas.pgm.mongo.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bas.pgm.model.User;

public interface UserRepo extends PagingAndSortingRepository<User, String> {

	public User findByPhoneAndPassword(String phone, String password);

	public User findByPhone(String phone);

	public User findByDeviceId(String deviceId);

}
