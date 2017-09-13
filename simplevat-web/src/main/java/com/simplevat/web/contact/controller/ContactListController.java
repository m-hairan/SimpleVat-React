package com.simplevat.web.contact.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Contact;

import com.simplevat.service.ContactService;

import com.simplevat.web.contact.model.ContactModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@SpringScopeView
public class ContactListController extends ContactHelper implements Serializable {

    private static final long serialVersionUID = 2549403337578048506L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactListController.class);

    @Getter
    @Setter
    private Map<Integer, Boolean> selectedContactIds = new HashMap<>();

    private List<ContactModel> filteredContacts;

    @Getter
    @Setter
    private String selectedFilter;

    @Getter
    @Setter
    private Contact selectedContact;

    @Getter
    @Setter
    private List<Contact> selectedContacts;

    @Getter
    @Setter
    private Map<String, String> filters;

    @Autowired
    private ContactService contactService;

    @PostConstruct
    public void init() {
        selectedFilter = null;
        this.filteredContacts = new ArrayList<>();
        populateContactList();
        populateFilters();
    }

    public void populateContactList() {
        if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
            filteredContacts = this.getFilteredContacts(this.selectedFilter);
        } else {
            for (Contact contact : contactService.getContacts()) {
                ContactModel contactModel = getContactModel(contact);
                filteredContacts.add(contactModel);
            }
        }
    }

    private void populateFilters() {
        filters = new HashMap<>();
        filteredContacts.stream().forEach(contact -> {
            if (contact.getFirstName() != null && !contact.getFirstName().isEmpty()) {
                String firstChar = contact.getFirstName().substring(0, 1).toUpperCase();
                if (!filters.containsKey(firstChar)) {
                    filters.put(firstChar, firstChar);
                }
            }
        });
    }

    private List<ContactModel> getFilteredContacts(String filterChar) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + filterChar);
            LOGGER.debug("Number of contacts :" + filteredContacts.size());
        }
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contact : filteredContacts) {
            if (contact.getFirstName() != null && !contact.getFirstName().isEmpty()) {
                if (contact.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                    returnContacts.add(contact);
                }
            }
        }
        return returnContacts;
    }

    public void delete() {
        for (ContactModel contactModel : getContacts()) {
            if (contactModel.getSelected()) {
                Contact contact = getContact(contactModel);
                contactService.update(contact);
            }
        }
        init();
    }

    @Nonnull
    public List<ContactModel> getContacts() {
        return filteredContacts;
    }

    public String redirectToEditContact() throws IOException {
//        contactModel = new ContactModel();
//        BeanUtils.copyProperties(contact, contactModel);
//        contactModel.setEmailAddress(contact.getEmail());
//        contactModel.setOrganizationName(contact.getOrganization());
//        LOGGER.debug("Redirecting to create new contact page");
//        if(contact.getContactId() == null){
//            contactService.persist(contact);
//        }else{
//            contactService.update(contact, contact.getContactId());
//        }
        return "contact?faces-redirect=true&selectedContactId=" + selectedContact.getContactId();
    }

    public void deleteContact(Contact contact) {
        contact.setDeleteFlag(Boolean.TRUE);
        contactService.update(contact);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Contact deleted SuccessFully"));
    }

//    public void setSelectedContactIds(Long key, Boolean Value){
//        selectedContactIds.put(key,Value);
//    }
}
