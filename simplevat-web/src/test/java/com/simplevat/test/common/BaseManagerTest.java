package com.simplevat.test.common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.simplevat.dao.ContactDao;
import com.simplevat.dao.CountryDao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.dao.LanguageDao;
import com.simplevat.dao.RoleDao;
import com.simplevat.dao.UserNewDao;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.invoice.InvoiceService;

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

	@Autowired
    protected InvoiceService invoiceService;

	@Autowired
	protected CountryService countryService;

	@Autowired
	protected CountryDao countryDao;

	@Autowired
	protected LanguageService languageService;
        
	@Autowired
    protected UserNewDao userDao;

}
