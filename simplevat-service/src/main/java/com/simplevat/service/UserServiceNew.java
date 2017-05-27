package com.simplevat.service;

import javax.faces.bean.ManagedBean;

import com.simplevat.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.simplevat.dao.Dao;

@SuppressWarnings("hiding")
@Service
@ManagedBean(name = "userServiceNew")
public class UserServiceNew<Integer, User>
	implements SimpleVatService<Integer, User>{

	@Autowired
    @Qualifier(value = "userDao")
	private Dao<Integer, User> dao;
	
	@Override
	public Dao<Integer, User> getDao() {
		return dao;
	}

}
