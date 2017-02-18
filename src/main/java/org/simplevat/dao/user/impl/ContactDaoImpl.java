/**
 * 
 */
package org.simplevat.dao.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.simplevat.dao.user.ContactDao;
import org.simplevat.domain.user.Contact;
import org.springframework.stereotype.Repository;

/**
 * @author MohsinH
 *
 */
@Repository
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

		Contact contact = new Contact(new Integer(1), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(2), "Asim", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(3), "Kashif", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(4), "Zubair", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(5), "Yaseen", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(6), "Sameen", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(7), "Muzamil", "Fawaz", "fawaz.asim@gmail.com","+971 0565610010","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(8), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(9), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(10), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(11), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(12), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(13), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(14), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(15), "Mohsin", "Hashmi", "myselfmohsin@gmail.com","+971 0566104818","user-avtar-icon1.png");
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
