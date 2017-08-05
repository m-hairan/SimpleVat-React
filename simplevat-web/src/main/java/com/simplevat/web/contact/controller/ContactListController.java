package com.simplevat.web.contact.controller;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import java.io.Serializable;
import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class ContactListController implements Serializable {

    private static final long serialVersionUID = 2549403337578048506L;
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactListController.class);

    @Getter
    @Setter
    private Map<Long, Boolean> selectedContactIds = new HashMap<>();

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
        selectedFilter = null;
        this.filteredContacts = new ArrayList<>();

    }

    public Map<String, String> getFilters() {
        filters = new HashMap<>();
//        filteredContacts.stream()
//                .map((contact) -> contact.getFirstName().substring(0, 1).toUpperCase())
//                .forEach((filterCharacter) -> {
//                    filters.put(filterCharacter, filterCharacter);
//                });
        return filters;
    }

    private List<Contact> getFilteredContacts(String filterChar) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + filterChar);
            LOGGER.debug("Number of contacts :" + filteredContacts.size());
        }
        List<Contact> returnContacts = new ArrayList<>();
        for (Contact contact : filteredContacts) {
            if (contact.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                returnContacts.add(contact);
            }
        }
        return returnContacts;
    }

    @Nonnull
    public List<Contact> getContacts() {
        if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
            filteredContacts = this.getFilteredContacts(this.selectedFilter);
        } else {
            filteredContacts = contactService.getContacts();
        }
        return filteredContacts;

    }

    public void deleteContact(@Nonnull final Contact contact) {
        contact.setDeleteFlag(Boolean.TRUE);
        contactService.createOrUpdateContact(contact);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Contact deleted SuccessFully"));
    }

}
