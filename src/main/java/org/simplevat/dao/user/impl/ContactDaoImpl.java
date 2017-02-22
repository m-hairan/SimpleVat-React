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
 * @author JohnH
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

		Contact contact = new Contact(new Integer(1), "John", "Clark", "John.Clark@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(2), "Alex", "Hoar", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(3), "Jemi", "Hoare", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(4), "Zin", "Hoare", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(5), "Yarn", "Hoare", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(6), "Sam", "Hoare", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(7), "Moz", "Hoare", "Hoare.17@gmail.com","+971 0505050505","user-avtar-icon2.png");
		contacts.add(contact);
		contact = new Contact(new Integer(8), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(9), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(10), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(11), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(12), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(13), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(14), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
		contacts.add(contact);
		contact = new Contact(new Integer(15), "John", "Clark", "myselfJohn@gmail.com","+971 565656565","user-avtar-icon1.png");
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
