/**
 * 
 */
package org.simplevat.service.user;

import java.util.List;

import org.simplevat.entity.user.Contact;

/**
 * @author MohsinH
 *
 */
public interface ContactService {
	
	public Contact createContact(Contact contact);
	
	public Contact editContact(Contact contact);
	
	public void deleteContact(Contact contact);
	
	public List<Contact> getContacts();
	
	public Contact getContactbyId(Integer contactId);

}
