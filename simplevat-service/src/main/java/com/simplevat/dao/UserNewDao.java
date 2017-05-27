package com.simplevat.dao;

import org.springframework.stereotype.Repository;

import com.simplevat.entity.User;

@Repository(value = "userDao")
public class UserNewDao extends AbstractDao<Integer, User> {

}
