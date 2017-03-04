package com.simplevat.test.common;
import com.simplevat.dao.ContactDao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.dao.LanguageDao;
import com.simplevat.dao.RoleDao;
import com.simplevat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.simplevat.service.ExpenseService;

@ContextConfiguration({"/spring/applicationContext.xml"})
public abstract class BaseManagerTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected  ExpenseService expenseService;
	
	@Autowired
	protected LanguageDao languageDao;

	@Autowired
	protected RoleDao roleDao;

	@Autowired
	protected ExpenseDao expenseDao;

	@Autowired
	protected ContactDao contactDao;

	@Autowired
	protected ContactService contactService;

}
