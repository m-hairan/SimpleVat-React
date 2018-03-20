package com.simplevat.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.entity.User;
import javax.persistence.TypedQuery;

@Repository(value = "userDao")
public class UserNewDaoImpl extends AbstractDao<Integer, User> implements UserNewDao {

    public Optional<User> getUserByEmail(String emailAddress) {
        Query query = this.getEntityManager().createQuery("SELECT u FROM User AS u WHERE u.userEmail =:email");
        query.setParameter("email", emailAddress);
        List resultList = query.getResultList();
        if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 1) {
            return Optional.of((User) resultList.get(0));
        }
        return Optional.empty();
    }

    public User getUserEmail(String emailAddress) {
        TypedQuery<User> query = this.getEntityManager().createQuery("SELECT u FROM User AS u WHERE u.userEmail =:email", User.class);
        query.setParameter("email", emailAddress);
        List<User> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean getUserByEmail(String usaerName, String password) {
        TypedQuery<User> query = getEntityManager().createQuery("SELECT u FROM User u WHERE u.userEmail =:userEmail AND u.password =:password", User.class);
        query.setParameter("userEmail", usaerName);
        query.setParameter("password", password);
        User user = query.getSingleResult();
        return user != null;
    }
}
