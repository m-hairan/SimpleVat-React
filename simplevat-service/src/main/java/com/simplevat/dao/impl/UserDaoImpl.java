package com.simplevat.dao.impl;

import com.simplevat.dao.UserDao;
import com.simplevat.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Hiren
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public User getUser(@Nonnull final int userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public void saveUser(@Nonnull final User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(@Nonnull final User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByEmail(String emailAddress) {
        Query query = entityManager.createQuery("SELECT u FROM user AS u WHERE u.userEmail =:email");
        query.setParameter("email", emailAddress);
        return (User) query.getSingleResult();
    }
}
