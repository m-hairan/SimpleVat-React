package com.simplevat.test;
import com.simplevat.dao.LanguageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.simplevat.service.ExpenseService;
import com.simplevat.service.TestService;

@ContextConfiguration({"/spring/applicationContext.xml"})
public abstract class BaseManagerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected  ExpenseService expenseService;
	
	@Autowired
	protected  TestService testService;

	@Autowired
	protected LanguageDao languageDao;
	

}
