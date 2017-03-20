package com.simplevat.dao;

import com.simplevat.entity.Contact;

import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
public interface ContactDao {

    List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);

    List<Contact> getContacts();

    List<Contact> getContacts(final String searchQuery);

    Contact createContact(Contact contact);

    Contact getContact(int id);

    Contact updateContact(Contact contact);
}
