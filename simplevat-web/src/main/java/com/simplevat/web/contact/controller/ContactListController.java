package com.simplevat.web.contact.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Contact;

import com.simplevat.service.ContactService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;

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
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@SpringScopeView
public class ContactListController extends BaseController implements Serializable {

    private static final long serialVersionUID = 2549403337578048506L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactListController.class);

    @Getter
    @Setter
    private Map<Integer, Boolean> selectedContactIds = new HashMap<>();

    private List<ContactModel> filteredContacts;
    private List<ContactModel> contactList;

    @Getter
    @Setter
    private String selectedFilter;

    @Getter
    @Setter
    private Contact selectedContact;

    @Getter
    @Setter
    private ContactModel contact;

    @Getter
    @Setter
    private List<Contact> selectedContacts;

    @Getter
    @Setter
    private Map<String, String> filters;

    @Autowired
    private ContactService contactService;

    @Getter
    @Setter
    private ContactHelper contactHelper;

    public ContactListController() {
        super(ModuleName.CONTACT_MODULE);
    }

    @PostConstruct
    public void init() {
        selectedFilter = null;
        contactHelper=new ContactHelper();
        contactList = new ArrayList<>();
        this.filteredContacts = new ArrayList<>();
        populateContactList();
        populateFilters();
    }

    public void populateContactList() {
        if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
            filteredContacts = this.getFilteredContacts(this.selectedFilter);
        } else {
            contactList.clear();
            for (Contact contact : contactService.getContacts()) {
                ContactModel contactModel = contactHelper.getContactModel(contact);
                contactList.add(contactModel);
            }
            filteredContacts.clear();
            filteredContacts.addAll(contactList);
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
            LOGGER.debug("Number of contacts :" + contactList.size());
        }
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contact : contactList) {
            if (contact.getFirstName() != null && !contact.getFirstName().isEmpty()) {
                if (contact.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                    returnContacts.add(contact);
                }
            }
        }
        return returnContacts;
    }

    public void delete() {
        for (ContactModel contactModel : filteredContacts) {
            if (contactModel.getSelected()) {
                Contact contact1 =contactHelper.getContact(contactModel);
                contact1.setDeleteFlag(Boolean.TRUE);
                contactService.update(contact1);
            }
        }
        init();
        RequestContext.getCurrentInstance().update("contactListForm");
    }

    @Nonnull
    public List<ContactModel> getContacts() {
        return filteredContacts;
    }

    public String redirectToEditContact() {
        return "contact?faces-redirect=true&selectedContactId=" + contact.getContactId();

    }

    public void deleteContact() {

        contact.setDeleteFlag(Boolean.TRUE);
        selectedContact = contactHelper.getContact(contact);

        contactService.update(selectedContact);
        populateContactList();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        context.addMessage(null, new FacesMessage("Contact deleted SuccessFully"));
    }

//    public void setSelectedContactIds(Long key, Boolean Value){
//        selectedContactIds.put(key,Value);
//    }
}
