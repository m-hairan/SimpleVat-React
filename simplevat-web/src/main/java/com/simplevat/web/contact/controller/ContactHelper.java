/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.contact.controller;

import com.simplevat.entity.Contact;
import com.simplevat.entity.ContactView;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.contact.model.ContactViewModel;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ContactHelper {

    public ContactModel getContactModel(Contact contact) {
        ContactModel contactModel = new ContactModel();
        contactModel.setContactId(contact.getContactId());
        contactModel.setBillingEmail(contact.getBillingEmail());
        contactModel.setCity(contact.getCity());
        contactModel.setContractPoNumber(contact.getContractPoNumber());
        contactModel.setCountry(contact.getCountry());
        contactModel.setCurrency(contact.getCurrency());
        contactModel.setEmail(contact.getEmail());
        contactModel.setFirstName(contact.getFirstName());
        contactModel.setInvoicingAddressLine1(contact.getInvoicingAddressLine1());
        contactModel.setInvoicingAddressLine2(contact.getInvoicingAddressLine2());
        contactModel.setInvoicingAddressLine3(contact.getInvoicingAddressLine3());
        contactModel.setLanguage(contact.getLanguage());
        contactModel.setLastName(contact.getLastName());
        contactModel.setMiddleName(contact.getMiddleName());
        contactModel.setMobileNumber(contact.getMobileNumber());
        contactModel.setOrganization(contact.getOrganization());
        contactModel.setPoBoxNumber(contact.getPoBoxNumber());
        contactModel.setPostZipCode(contact.getPostZipCode());
        contactModel.setStateRegion(contact.getStateRegion());
        contactModel.setTelephone(contact.getTelephone());
        contactModel.setTitle(contact.getTitle());
        contactModel.setVatRegistrationNumber(contact.getVatRegistrationNumber());
        contactModel.setVersionNumber(contact.getVersionNumber());
        contactModel.setCreatedBy(contact.getCreatedBy());
        contactModel.setCreatedDate(contact.getCreatedDate());
        contactModel.setLastUpdateDate(contact.getLastUpdateDate());
        contactModel.setLastUpdatedBy(contact.getLastUpdatedBy());
        contactModel.setDeleteFlag(contact.getDeleteFlag());
        for (ContactType contactType : ContactUtil.contactTypeList()) {
            if (contactType.getId() == contact.getContactType()) {
                contactModel.setContactType(contactType);
            }
        }
        return contactModel;
    }

    public Contact getContact(ContactModel contactModel) {
        Contact contact = new Contact();
        contact.setContactId(contactModel.getContactId());
        contact.setBillingEmail(contactModel.getBillingEmail());
        contact.setCity(contactModel.getCity());
        contact.setContractPoNumber(contactModel.getContractPoNumber());
        contact.setCountry(contactModel.getCountry());
        contact.setCurrency(contactModel.getCurrency());
        contact.setEmail(contactModel.getEmail());
        contact.setFirstName(contactModel.getFirstName());
        contact.setInvoicingAddressLine1(contactModel.getInvoicingAddressLine1());
        contact.setInvoicingAddressLine2(contactModel.getInvoicingAddressLine2());
        contact.setInvoicingAddressLine3(contactModel.getInvoicingAddressLine3());
        contact.setLanguage(contactModel.getLanguage());
        contact.setLastName(contactModel.getLastName());
        contact.setMiddleName(contactModel.getMiddleName());
        contact.setMobileNumber(contactModel.getMobileNumber());
        contact.setOrganization(contactModel.getOrganization());
        contact.setPoBoxNumber(contactModel.getPoBoxNumber());
        contact.setPostZipCode(contactModel.getPostZipCode());
        contact.setStateRegion(contactModel.getStateRegion());
        contact.setTelephone(contactModel.getTelephone());
        contact.setTitle(contactModel.getTitle());
        contact.setVatRegistrationNumber(contactModel.getVatRegistrationNumber());
        contact.setVersionNumber(contactModel.getVersionNumber());
        contact.setCreatedBy(contactModel.getCreatedBy());
        contact.setCreatedDate(contactModel.getCreatedDate());
        contact.setLastUpdateDate(contactModel.getLastUpdateDate());
        contact.setLastUpdatedBy(contactModel.getLastUpdatedBy());
        contact.setDeleteFlag(contactModel.getDeleteFlag());
        contact.setContactType(contactModel.getContactType().getId());
        return contact;
    }

    public ContactView getContactView(ContactViewModel contactViewModel) {
        ContactView contactView = new ContactView();
        contactView.setContactId(contactViewModel.getContactId());
        contactView.setEmail(contactViewModel.getEmail());
        contactView.setFirstName(contactViewModel.getFirstName());
        contactView.setLastName(contactViewModel.getLastName());
        contactView.setMiddleName(contactViewModel.getMiddleName());
        contactView.setOrganization(contactViewModel.getOrganization());
        contactView.setTelephone(contactViewModel.getTelephone());
        contactView.setTitle(contactViewModel.getTitle());
        contactView.setCurrencySymbol(contactViewModel.getCurrencySymbol());
        contactView.setNextDueDate(contactViewModel.getNextDueDate());
        contactView.setDueAmount(contactViewModel.getDueAmount());
        contactView.setContactType(contactViewModel.getContactType().getId());
        return contactView;
    }

    public ContactViewModel getContactViewModel(ContactView contactView) {
        ContactViewModel contactViewModel = new ContactViewModel();
        contactViewModel.setContactId(contactView.getContactId());
        contactViewModel.setEmail(contactView.getEmail());
        contactViewModel.setFirstName(contactView.getFirstName());
        contactViewModel.setLastName(contactView.getLastName());
        contactViewModel.setMiddleName(contactView.getMiddleName());
        contactViewModel.setOrganization(contactView.getOrganization());
        contactViewModel.setTelephone(contactView.getTelephone());
        contactViewModel.setNextDueDate(contactView.getNextDueDate());
        contactViewModel.setTitle(contactView.getTitle());
        contactViewModel.setCurrencySymbol(contactView.getCurrencySymbol());
        contactViewModel.setDueAmount(contactView.getDueAmount());
        for (ContactType contactType : ContactUtil.contactTypeList()) {
            if (contactType.getId() == contactView.getContactType()) {
                contactViewModel.setContactType(contactType);
            }
        }
        return contactViewModel;
    }

}
