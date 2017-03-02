package com.simplevat.dao.impl;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.CommonDao;
import com.simplevat.dao.TestDao;
import com.simplevat.entity.TestEntity;

@Repository
public class TestDaoImpl extends CommonDao implements TestDao  {

	public void saveTest(TestEntity test) {
		this.getSessionFactory().getCurrentSession().save(test);
		this.getSessionFactory().getCurrentSession().flush();
	}

}
