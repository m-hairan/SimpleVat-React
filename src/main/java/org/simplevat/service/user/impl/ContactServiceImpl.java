/**
 * 
 */
package org.simplevat.service.user.impl;

import java.util.List;

import org.simplevat.dao.user.ContactDao;
import org.simplevat.entity.user.Contact;
import org.simplevat.service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * @author MohsinH
 *
 */

@Service
public class ContactServiceImpl implements ContactService {


	@Qualifier("contactDaoImpl")
	@Autowired
	private ContactDao contactDao;

	/* (non-Javadoc)
	 * @see org.simplevat.service.ContactService#createContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public Contact createContact(Contact contact) {
		
		return contactDao.createContact(contact);
	}

	/* (non-Javadoc)
	 * @see org.simplevat.service.ContactService#editContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public Contact editContact(Contact contact) {
		
		return contactDao.editContact(contact);
	}

	/* (non-Javadoc)
	 * @see org.simplevat.service.ContactService#deleteContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public void deleteContact(Contact contact) {
		contactDao.deleteContact(contact);

	}
	
	/* (non-Javadoc)
	 * @see org.simplevat.service.ContactService#getContacts()
	 */
	@Override
	public List<Contact> getContacts() {
		
		return contactDao.getContacts();
	}

	/* (non-Javadoc)
	 * @see org.simplevat.service.ContactService#getContactbyId()
	 */
	@Override
	public Contact getContactbyId(Integer contactId) {
		
		return contactDao.getContactbyId(contactId);
	}

	/**
	 * @return the contactDao
	 */
	public ContactDao getContactDao() {
		return contactDao;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}


}
