package com.simplevat.service.impl;

import com.simplevat.dao.UserDao;
import com.simplevat.entity.User;
import com.simplevat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 *
 * @author Hiren
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> getUserByEmail(String emailAddress){
        return userDao.getUserByEmail(emailAddress);
    }

    @Override
    public User getUser(@Nonnull final int userId) {
        return userDao.getUser(userId);
    }

    @Override
    public void saveUser(@Nonnull final User user) {
        userDao.saveUser(user);
    }

    @Override
    public void updateUser(@Nonnull final User user) {
        userDao.updateUser(user);
    }

}
