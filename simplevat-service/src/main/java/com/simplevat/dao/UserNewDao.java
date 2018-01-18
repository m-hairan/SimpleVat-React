package com.simplevat.dao;

import java.util.Optional;

import com.simplevat.entity.User;

public interface UserNewDao extends Dao<Integer, User> {

    public Optional<User> getUserByEmail(String emailAddress);

    public boolean getUserByEmail(String usaerName, String password);
}
