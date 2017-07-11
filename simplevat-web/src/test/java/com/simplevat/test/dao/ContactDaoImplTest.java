package com.simplevat.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Title;

@ContextConfiguration({ "/spring/applicationContext.xml" })
public class ContactDaoImplTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private ContactDao contactDao;
	
	private int contactId;
	
	@Before
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setUp() {
		Contact contact = getNewContact();
		contact = contactDao.persist(contact);
		assertNotNull(contact);
		assertTrue("contactId should not be zero ", contact.getContactId() > 0);
		assertTrue("firstName not matching ", contact.getFirstName().equals("firstName"));
		contactId = contact.getContactId();
	}
	
	@Test
	public void testUpdateNVersion() {
		Contact contact = contactDao.findByPK(contactId);
		assertNotNull(contact);
		assertTrue("contactId not matching ", contact.getContactId() == contactId);
		contact.setFirstName("firstNameChanged");
		contact = contactDao.update(contact);
		assertNotNull(contact);
		assertTrue("contactId not matching ", contact.getContactId() == contactId);
		assertTrue("firstNameChanged not reflecting", contact.getFirstName().equals("firstNameChanged"));
		assertTrue("Version Number Should be 1", contact.getVersionNumber() == 1);
		
		contact.setLastName("lastNameChanged");
		contact = contactDao.update(contact);
		assertNotNull(contact);
		assertTrue("contactId not matching ", contact.getContactId() == contactId);
		assertTrue("lastNameChanged not reflecting", contact.getLastName().equals("lastNameChanged"));	
		assertTrue("Version Number Should be 2", contact.getVersionNumber() == 2);
		
		contact.setMiddleName("middleNameChanged");
		contact = contactDao.update(contact);
		assertNotNull(contact);
		assertTrue("contactId not matching ", contact.getContactId() == contactId);
		assertTrue("middleNameChanged not reflecting", contact.getMiddleName().equals("middleNameChanged"));	
		assertTrue("Version Number Should be 2", contact.getVersionNumber() == 3);
	}

	
	@After
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void tearDown() {
		Contact contact = contactDao.findByPK(contactId);
		assertNotNull(contact);
		assertTrue("contactId not matching ", contact.getContactId() == contactId);
		contactDao.delete(contact);
		contact = contactDao.findByPK(contactId);
		assertNull(contact);
	}
	
	
	private Contact getNewContact() {
		Contact contact = new Contact();
		contact.setBillingEmail("firstName.secondName@gmail.com");
		contact.setCity("city");
		contact.setContractPoNumber("poNumber");
		
		Country country = new Country();
		country.setCountryCode(1);
		contact.setCountry(country);
		
		contact.setCreatedBy(3);
		
		Currency currency = new Currency();
		currency.setCurrencyCode(1);
		contact.setCurrency(currency);
		
		contact.setEmail("firstName.secondName@gmail.com");
		
		contact.setFirstName("firstName");
		contact.setInvoicingAddressLine1("");
		contact.setInvoicingAddressLine2("");
		contact.setInvoicingAddressLine3("");
		
		Language lang = new Language();
		lang.setLanguageCode(1);
		contact.setLanguage(lang);
		
		contact.setLastName("lastName");
		contact.setMiddleName("middleName");
		contact.setMobileNumber("9921214259");
		contact.setOrganization("Organization");
		contact.setPoBoxNumber("PoBoxNumber");
		contact.setPostZipCode("postZipCode");
		contact.setStateRegion("stateRegion");
		contact.setTelephone("Telephone");
		
		Title title = new Title();
		title.setTitleCode(1);
		contact.setTitle(title);
		contact.setVatRegistrationNumber("vat");
		
		return contact;
		
	}

}
