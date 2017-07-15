package com.simplevat.service;

import java.util.List;

import com.simplevat.entity.User;

public interface UserServiceNew extends SimpleVatService<Integer, User> {
	
	public List<User> findAllUsers();

}
