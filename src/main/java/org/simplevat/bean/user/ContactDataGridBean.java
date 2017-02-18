package org.simplevat.bean.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.simplevat.domain.user.Contact;
import org.simplevat.service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@ManagedBean(name = "contactDataGridBean")
@ViewScoped
public class ContactDataGridBean implements Serializable {

	final static Logger logger = Logger.getLogger(ContactDataGridBean.class);
	
	private List<Contact> contacts;
	
	private Contact selectedContact;

	@Qualifier("contactServiceImpl")
	@Autowired
	private ContactService contactService;
	
	
	@PostConstruct
    public void init() {

		if(logger.isDebugEnabled()){
			logger.debug("Initializing manages bean");
		}
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
