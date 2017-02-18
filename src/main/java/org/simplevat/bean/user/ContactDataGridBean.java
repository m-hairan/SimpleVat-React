package org.simplevat.bean.user;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.simplevat.domain.user.Contact;
import org.simplevat.service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "contactDataGridBean")
@ViewScoped
public class ContactDataGridBean implements Serializable {

	final static Logger logger = Logger.getLogger(ContactDataGridBean.class);
	
	private List<Contact> contacts;

    private List<Contact> filteredContacts;

    private String selectedFilter;
	
	private Contact selectedContact;

	private Map<String,String> filters;

	@Qualifier("contactServiceImpl")
	@Autowired
	private ContactService contactService;
	
	
	@PostConstruct
    public void init() {

		this.contacts = contactService.getContacts();
		this.filteredContacts = this.contacts;

    }

    /**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {

		if(logger.isDebugEnabled()){
			logger.debug("Returning all contacts");
		}
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

    public Map<String, String> getFilters() {
        filters = new TreeMap<String, String>();
        Iterator<Contact> contactIterator = contacts.iterator();
        while (contactIterator.hasNext()) {
            Contact contact = contactIterator.next();
            filters.put(contact.getContactFirstName().substring(0,1).toUpperCase(),contact.getContactFirstName().substring(0,1).toUpperCase());
            logger.debug("Contact Filter Char :"+contact.getContactFirstName().substring(0,1));
        }
        return filters;
    }

    public List<Contact> getFilteredContacts() {
        return filteredContacts;
    }

    public void setFilteredContacts(List<Contact> filteredContacts) {
        this.filteredContacts = filteredContacts;
    }

    public String getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(String selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public void onContactFilterChange() {
        if(logger.isDebugEnabled()){
            logger.debug("Changing Filter to :"+this.selectedFilter);
        }
        this.contacts = contactService.getContacts();
	    if (this.selectedFilter == null || this.selectedFilter.isEmpty()) {
            this.filteredContacts = contacts;
        }
        else {
	        this.filteredContacts = this.getFilteredContacts(this.selectedFilter);
        }
    }

    private List<Contact> getFilteredContacts(String filterChar) {
        if(logger.isDebugEnabled()){
            logger.debug("Changing Filter to :"+filterChar);
            logger.debug("Number of contacts :"+contacts.size());
        }
	    List<Contact> returnContacts = new ArrayList<Contact>();
        Iterator<Contact> contactIterator = contacts.iterator();
        while (contactIterator.hasNext()) {
            Contact contact = contactIterator.next();
            if (contact.getContactFirstName().substring(0,1).toUpperCase().equals(filterChar.toUpperCase())) {
                returnContacts.add(contact);
            }
        }
	    return  returnContacts;
    }
}
