package com.simplevat.controller.contact;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.*;

@Controller
@ManagedBean(name = "contactListController")
@RequestScoped
public class ContactListController implements Serializable {

    private static final long serialVersionUID = 2549403337578048506L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactListController.class);


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
    private Map<String, String> filters;


    @Autowired
    private ContactService contactService;


    @PostConstruct
    public void init() {

        this.contacts = contactService.getContacts();
        this.filteredContacts = this.contacts;
        LOGGER.debug("Fetched Contacts :" + contacts.size());

    }

    public Map<String, String> getFilters() {
        filters = new HashMap<>();
        for (Contact contact : contacts) {
            String filterCharacter = contact.getFirstName().substring(0, 1).toUpperCase();
            filters.put(filterCharacter, filterCharacter);
            LOGGER.debug("Contact Filter Char :" + filterCharacter);
        }
        return filters;
    }

    public void onContactFilterChange() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + this.selectedFilter);
        }
        this.contacts = this.contacts;//contactService.getContacts();
        if (this.selectedFilter == null || this.selectedFilter.isEmpty()) {
            this.filteredContacts = contacts;
        } else {
            this.filteredContacts = this.getFilteredContacts(this.selectedFilter);
        }
    }

    private List<Contact> getFilteredContacts(String filterChar) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + filterChar);
            LOGGER.debug("Number of contacts :" + contacts.size());
        }
        List<Contact> returnContacts = new ArrayList<Contact>();
        for (Contact contact : contacts) {
            if (contact.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                returnContacts.add(contact);
            }
        }
        return returnContacts;
    }

}
