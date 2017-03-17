package com.simplevat.service;

import com.simplevat.entity.Contact;

import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
public interface ContactService {

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);

    public List<Contact> getContacts();
    
    public List<Contact> getContacts(final String searchQuery);

    public Contact createContact(Contact contact);
    
     public Contact getContact(int id);
}
