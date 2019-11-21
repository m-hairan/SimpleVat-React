package com.simplevat.dao;

import com.simplevat.entity.Contact;
import com.simplevat.entity.ContactView;

import java.util.List;
import java.util.Optional;

/**
 * Created by mohsin on 3/3/2017.
 */
public interface ContactDao extends Dao<Integer, Contact> {

    List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);

    List<Contact> getContacts();

    List<ContactView> getContactViewList();

    List<Contact> getContacts(final String searchQuery, int contactType);

    public Contact getLastContact();

    /*   Contact createContact(Contact contact);

    Contact getContact(int id);

    Contact updateContact(Contact contact);*/
    Optional<Contact> getContactByEmail(String Email);

    void deleteByIds(List<Integer> ids);
}
