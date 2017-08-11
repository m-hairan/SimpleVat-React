/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.contact.controller;

import com.simplevat.entity.Contact;
import com.simplevat.web.contact.model.ContactModel;

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
        return contactModel;
    }
}
