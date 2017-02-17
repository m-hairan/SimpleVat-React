package org.simplevat.view.user;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.simplevat.domain.user.Contact;
import org.simplevat.service.ContactService;

@ManagedBean
@ViewScoped
public class ContactDataGridView implements Serializable {
	
	private List<Contact> contacts;
	
	private Contact selectedContact;
	
	public ContactService contactService;
	
	
	@PostConstruct
    public void init() {   
		
		contacts = contactService.getContacts();
    }


	/**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the selectedContact
	 */
	public Contact getSelectedContact() {
		return selectedContact;
	}

	/**
	 * @param selectedContact the selectedContact to set
	 */
	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
	}
	
	

}
