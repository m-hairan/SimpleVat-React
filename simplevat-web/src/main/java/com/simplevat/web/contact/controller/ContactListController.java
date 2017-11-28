package com.simplevat.web.contact.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.invoice.Invoice;

import com.simplevat.service.ContactService;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;

import com.simplevat.web.contact.model.ContactModel;

import java.io.Serializable;
import java.time.ZoneId;
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
        if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
            filteredContacts = this.getFilteredContacts(this.selectedFilter);
        } else {
            contactList.clear();
            for (Contact contact : contactService.getContacts()) {
                ContactModel contactModel = contactHelper.getContactModel(contact);
                contactCountByType(contactModel);
                contactList.add(contactModel);
            }
            filteredContacts = contactList;
        }
    }

    private void contactCountByType(ContactModel contactModel) {
        if (contactModel.getContactType() != null) {
            if (contactModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                totalEmployees++;
            } else if (contactModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                Invoice invoice = invoiceService.getClosestDueInvoiceByContactId(contactModel.getContactId());
                Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant());
                contactModel.setClosestDueDate(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant()));
                contactModel.setDueAmount(invoice.getDueAmount());
                totalCustomers++;
            } else {
                Purchase purchase = purchaseService.getClosestDuePurchaseByContactId(contactModel.getContactId());
                Date.from(purchase.getPurchaseDueDate().atZone(ZoneId.systemDefault()).toInstant());
                contactModel.setClosestDueDate(Date.from(purchase.getPurchaseDueDate().atZone(ZoneId.systemDefault()).toInstant()));
                contactModel.setDueAmount(purchase.getPurchaseDueAmount());
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

    private List<ContactModel> getFilteredContacts(String filterChar) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Changing Filter to :" + filterChar);
            LOGGER.debug("Number of contacts :" + contactList.size());
        }
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contactModel : contactList) {
            if (contactModel.getFirstName() != null && !contactModel.getFirstName().isEmpty()) {
                if (contactModel.getFirstName().substring(0, 1).toUpperCase().equals(filterChar.toUpperCase())) {
                    contactCountByType(contactModel);
                    returnContacts.add(contactModel);
                }
            }
        }
        return returnContacts;
    }

    public void delete() {
        for (ContactModel contactModel : filteredContacts) {
            if (contactModel.getSelected()) {
                Contact contact1 = contactHelper.getContact(contactModel);
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

    public void populateContactEmployeeList() {
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contactModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                    if (contactModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                        returnContacts.add(contactModel);
                    }
                }
            } else {
                if (contactModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                    returnContacts.add(contactModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

    public void populateContactVendorList() {
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contactModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                    if (contactModel.getContactType().getId() == ContactTypeConstant.VENDOR) {
                        returnContacts.add(contactModel);
                    }
                }
            } else {
                if (contactModel.getContactType().getId() == ContactTypeConstant.VENDOR) {
                    returnContacts.add(contactModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

    public void populateContactCustomerList() {
        List<ContactModel> returnContacts = new ArrayList<>();
        for (ContactModel contactModel : contactList) {
            if (this.selectedFilter != null && !this.selectedFilter.isEmpty()) {
                if (contactModel.getFirstName().substring(0, 1).toUpperCase().equals(selectedFilter.toUpperCase())) {
                    if (contactModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                        returnContacts.add(contactModel);
                    }
                }
            } else {
                if (contactModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                    returnContacts.add(contactModel);
                }
            }
        }
        filteredContacts = returnContacts;
    }

}
