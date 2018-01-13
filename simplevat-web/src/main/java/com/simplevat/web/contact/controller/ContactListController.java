package com.simplevat.web.contact.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Contact;
import com.simplevat.entity.ContactView;

import com.simplevat.service.ContactService;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;

import com.simplevat.web.contact.model.ContactViewModel;

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

    @Autowired
    private ContactService contactService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PurchaseService purchaseService;

    @Getter
    @Setter
    private Map<Integer, Boolean> selectedContactIds = new HashMap<>();

    private List<ContactViewModel> filteredContacts;
    private List<ContactViewModel> contactList;

    @Getter
    @Setter
    private String selectedFilter;

    @Getter
    @Setter
    private Contact selectedContact;

    @Getter
    @Setter
    private ContactViewModel contact;

    @Getter
    @Setter
    private List<Contact> selectedContacts;

    @Getter
    @Setter
    private Map<String, String> filters;

    @Getter
    @Setter
    private int totalEmployees;

    @Getter
    @Setter
    private int totalVendors;

    @Getter
    @Setter
    private int totalCustomers;

    @Getter
    @Setter
    private int totalContacts;

    @Getter
    @Setter
    private ContactHelper contactHelper;

    public ContactListController() {
        super(ModuleName.CONTACT_MODULE);
    }

    @PostConstruct
    public void init() {
        selectedFilter = null;
        contactHelper = new ContactHelper();
        contactList = new ArrayList<>();
        this.filteredContacts = new ArrayList<>();
        populateContactList();
        populateFilters();
    }

    public void populateContactList() {
        totalEmployees = 0;
        totalCustomers = 0;
        totalVendors = 0;
        totalContacts = 0;
        Date startTime = new Date();
        if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
            filteredContacts = this.getFilteredContacts(this.selectedFilter);
        } else {
            contactList.clear();
            List<ContactView> contactViewList = contactService.getContactViewList();
            if (contactViewList != null) {
                for (ContactView contactView : contactViewList) {
                    ContactViewModel contactViewModel = contactHelper.getContactViewModel(contactView);
                    contactCountByType(contactViewModel);
                    contactList.add(contactViewModel);
                }
            }
            filteredContacts = contactList;
        }
        Date endTime = new Date();

        System.out.println("Time Required : " + (endTime.getTime() - startTime.getTime()) / 1000);
    }

    private void contactCountByType(ContactViewModel contactViewModel) {
        if (contactViewModel.getContactType() != null) {
            if (contactViewModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                totalEmployees++;
            } else if (contactViewModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                totalCustomers++;
            } else {
                totalVendors++;
            }
        }
        totalContacts++;
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

    private List<ContactViewModel> getFilteredContacts(String filterChar) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + filterChar);
            LOGGER.debug("Number of contacts :" + contactList.size());
        }
        List<ContactViewModel> returnContacts = new ArrayList<>();
        for (ContactViewModel contactViewModel : contactList) {
            if (contactViewModel.getFirstName() != null && !contactViewModel.getFirstName().isEmpty()) {
                if (contactViewModel.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                    contactCountByType(contactViewModel);
                    returnContacts.add(contactViewModel);
                }
            }
        }
        return returnContacts;
    }

    public void delete() {
        for (ContactViewModel contactViewModel : filteredContacts) {
            if (contactViewModel.getSelected()) {
                Contact contact1 = contactService.findByPK(contactViewModel.getContactId());
                contact1.setDeleteFlag(Boolean.TRUE);
                contactService.update(contact1);
            }
        }
        init();
        RequestContext.getCurrentInstance().update("contactListForm");
    }

    @Nonnull
    public List<ContactViewModel> getContacts() {
        return filteredContacts;
    }

    public String redirectToEditContact() {
        return "contact?faces-redirect=true&selectedContactId=" + contact.getContactId();

    }

    public void deleteContact() {
        Contact contact1 = contactService.findByPK(contact.getContactId());
        contact1.setDeleteFlag(Boolean.TRUE);
        contactService.update(contact1);
        populateContactList();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        context.addMessage(null, new FacesMessage("Contact deleted SuccessFully"));
    }

    public void populateContactEmployeeList() {
        List<ContactViewModel> returnContacts = new ArrayList<>();
        for (ContactViewModel contactViewModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactViewModel.getFirstName() != null && !contactViewModel.getFirstName().isEmpty()) {
                    if (contactViewModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                        if (contactViewModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                            returnContacts.add(contactViewModel);
                        }
                    }
                }
            } else {
                if (contactViewModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                    returnContacts.add(contactViewModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

    public void populateContactVendorList() {
        List<ContactViewModel> returnContacts = new ArrayList<>();
        for (ContactViewModel contactViewModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactViewModel.getFirstName() != null && !contactViewModel.getFirstName().isEmpty()) {
                    if (contactViewModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                        if (contactViewModel.getContactType().getId() == ContactTypeConstant.VENDOR) {
                            returnContacts.add(contactViewModel);
                        }
                    }
                }
            } else {
                if (contactViewModel.getContactType().getId() == ContactTypeConstant.VENDOR) {
                    returnContacts.add(contactViewModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

    public void populateContactCustomerList() {
        List<ContactViewModel> returnContacts = new ArrayList<>();
        for (ContactViewModel contactViewModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactViewModel.getFirstName() != null && !contactViewModel.getFirstName().isEmpty()) {
                    if (contactViewModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                        if (contactViewModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                            returnContacts.add(contactViewModel);
                        }
                    }
                }
            } else {
                if (contactViewModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                    returnContacts.add(contactViewModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

    public String contactCreateIncvoiceOrExpense() {
        if (contact.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
            return "/pages/secure/invoice/invoice?faces-redirect=true&contactId=" + contact.getContactId();
        } else if (contact.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
            return "/pages/secure/expense/expense?faces-redirect=true&contactId=" + contact.getContactId();
        } else if (contact.getContactType().getId() == ContactTypeConstant.VENDOR) {
            return "/pages/secure/purchase/purchase?faces-redirect=true&contactId=" + contact.getContactId();
        }
        return null;
    }

}
