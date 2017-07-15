package com.simplevat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.simplevat.dao.Dao;
import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;

@Service("userServiceNew")
public class UserServiceNewImpl implements UserServiceNew {

	@Autowired
    @Qualifier(value = "userDao")
	private Dao<Integer, User> dao;
	
	@Override
	public Dao<Integer, User> getDao() {
		return dao;
	}

	@Override
	public List<User> findAllUsers() {
		return this.executeNamedQuery("findAllUsers");
	}

}
