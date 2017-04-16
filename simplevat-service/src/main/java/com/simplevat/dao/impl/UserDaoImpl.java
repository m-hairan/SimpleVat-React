package com.simplevat.dao.impl;

import com.simplevat.dao.UserDao;
import com.simplevat.entity.User;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
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

}
