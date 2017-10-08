package com.simplevat.service;

import com.simplevat.entity.Contact;
import java.util.List;
import java.util.Optional;

/**
 * Created by mohsin on 3/3/2017.
 */
public abstract class ContactService extends SimpleVatService <Integer, Contact> {

    public abstract List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);

    public abstract List<Contact> getContacts();

    public abstract List<Contact> getContacts(final String searchQuery);

    public abstract Contact getContact(int id);
    
    public abstract Optional<Contact> getContactByEmail(String Email);

}
