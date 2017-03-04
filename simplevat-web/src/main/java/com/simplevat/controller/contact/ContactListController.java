package com.simplevat.controller.contact;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@ManagedBean
@ViewScoped
public class ContactListController implements Serializable {

	final static Logger logger = Logger.getLogger(ContactListController.class);

    @Getter
    @Setter
	private String welcomeMSG = "Welcome Mohsin";

	@Getter
    @Setter
	private List<Contact> contacts;

	@Getter
    @Setter
    private List<Contact> filteredContacts;

	@Getter
    @Setter
    private String selectedFilter;

    @Getter
    @Setter
	private Contact selectedContact;

    @Getter
	private Map<String,String> filters;


	@Autowired
	private ContactService contactService;
	
	
	@PostConstruct
    public void init() {

		this.contacts = contactService.getContacts(new Integer(1), new Integer(50));
		this.filteredContacts = this.contacts;
        logger.debug("Fetched Contacts :"+contacts.size());

    }

    public Map<String, String> getFilters() {
        filters = new TreeMap<String, String>();
        Iterator<Contact> contactIterator = contacts.iterator();
        while (contactIterator.hasNext()) {
            Contact contact = contactIterator.next();
            filters.put(contact.getFirstName().substring(0,1).toUpperCase(),contact.getLastName().substring(0,1).toUpperCase());
            logger.debug("Contact Filter Char :"+contact.getFirstName().substring(0,1));
        }
        return filters;
    }

    public void onContactFilterChange() {
        if(logger.isDebugEnabled()){
            logger.debug("Changing Filter to :"+this.selectedFilter);
        }
        this.contacts = contactService.getContacts(new Integer(1), new Integer(50));
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
            if (contact.getFirstName().substring(0,1).toUpperCase().equals(filterChar.toUpperCase())) {
                returnContacts.add(contact);
            }
        }
	    return  returnContacts;
    }
}
