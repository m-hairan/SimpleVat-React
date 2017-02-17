/**
 * 
 */
package org.simplevat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.simplevat.dao.ContactDao;
import org.simplevat.domain.user.Contact;

/**
 * @author MohsinH
 *
 */
public class ContactDaoImpl implements ContactDao {

	/* (non-Javadoc)
	 * @see org.simplevat.dao.ContactDao#createContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public Contact createContact(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.simplevat.dao.ContactDao#editContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public Contact editContact(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.simplevat.dao.ContactDao#deleteContact(org.simplevat.domain.user.Contact)
	 */
	@Override
	public void deleteContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.simplevat.dao.ContactDao#getContacts()
	 */
	@Override
	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();

		Contact contact = new Contact(new Integer(1), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","0566104818","image url");
		contacts.add(contact);
		contact = new Contact(new Integer(2), "Asim", "Fawaz", "fawaz.asim@gmail.com","0565610010","image url fawaz");
		contacts.add(contact);
		return contacts;
	}

	/* (non-Javadoc)
	 * @see org.simplevat.dao.ContactDao#getContactbyId()
	 */
	@Override
	public Contact getContactbyId(Integer contactId) {
		// TODO Auto-generated method stub
		return null;
	}

}
