package com.simplevat.controller.contact;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ManagedBean(name = "contactListController")
@RequestScoped
public class ContactListController implements Serializable {

	final static Logger logger = Logger.getLogger(ContactListController.class);

	@Getter
    @Setter
    private Map<Long, Boolean> selectedContactIds = new HashMap<Long, Boolean>();

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
    @Setter
    private List<Contact> selectedContacts;

    @Setter
	private Map<String,String> filters;


	@Autowired
	private ContactService contactService;
	
	
	@PostConstruct
    public void init() {

		this.contacts = contactService.getContacts();
		this.filteredContacts = this.contacts;
        logger.debug("Fetched Contacts :"+contacts.size());

    }

    public Map<String, String> getFilters() {
        filters = new TreeMap<String, String>();
        Iterator<Contact> contactIterator = contacts.iterator();
        while (contactIterator.hasNext()) {
            Contact contact = contactIterator.next();
            String filterCharacter = contact.getFirstName().substring(0,1).toUpperCase();
            filters.put(filterCharacter,filterCharacter);
            logger.debug("Contact Filter Char :"+filterCharacter);
        }
        return filters;
    }

    public void onContactFilterChange() {
        if(logger.isDebugEnabled()){
            logger.debug("Changing Filter to :"+this.selectedFilter);
        }
        this.contacts = this.contacts;//contactService.getContacts();
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

    public String redirectToCreateContact()
    {
        logger.debug("Redirecting to create new contact page");
        return "/pages/secure/contact/contact.xhtml";
    }
}
