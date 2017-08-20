package com.simplevat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.simplevat.dao.UserNewDao;
import com.simplevat.entity.User;
import com.simplevat.service.UserServiceNew;

@Service("userServiceNew")
public class UserServiceNewImpl extends UserServiceNew {

	@Autowired
    @Qualifier(value = "userDao")
	private UserNewDao dao;
	
	@Override
	public UserNewDao getDao() {
		return dao;
	}

	@Override
	public List<User> findAll() {
		return this.executeNamedQuery("findAllUsers");
	}

	@Override
	public Optional<User> getUserByEmail(String emailAddress) {
		return getDao().getUserByEmail(emailAddress);
	}

}
