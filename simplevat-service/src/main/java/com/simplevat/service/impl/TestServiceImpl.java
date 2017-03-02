package com.simplevat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.TestDao;
import com.simplevat.entity.TestEntity;
import com.simplevat.service.TestService;

@Service("testService")
@Transactional(readOnly = false)
public class TestServiceImpl implements TestService  {

	@Autowired
    public TestDao testDao;
	
	public void saveTest(TestEntity test) {
		testDao.saveTest(test);
	}

}
