package com.simplevat.service;

import java.util.List;
import java.util.Optional;

import com.simplevat.entity.User;

public interface UserServiceNew extends SimpleVatService<Integer, User> {
	
	
	  	Optional<User> getUserByEmail(String emailAddress);
	    
	    List<User> findAll();

}
