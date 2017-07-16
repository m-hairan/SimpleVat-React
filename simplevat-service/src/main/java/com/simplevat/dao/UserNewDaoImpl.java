package com.simplevat.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.entity.User;

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
}
