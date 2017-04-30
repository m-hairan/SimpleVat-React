package com.simplevat.dao.impl;

import com.simplevat.dao.UserDao;
import com.simplevat.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

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
    public Optional<User> getUserByEmail(String emailAddress) {
        Query query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.userEmail =:email");
        query.setParameter("email", emailAddress);
        List resultList = query.getResultList();
        if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 1) {
            return Optional.of((User) resultList.get(0));
        }
        return Optional.empty();
    }
}
