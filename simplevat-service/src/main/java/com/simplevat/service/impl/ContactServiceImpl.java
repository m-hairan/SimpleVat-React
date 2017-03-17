package com.simplevat.service.impl;

import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */

@Service("contactService")
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactDao contactDao;

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs) {

        return this.contactDao.getContacts(pageIndex,noOfRecorgs);
    }

    @Override
    public List<Contact> getContacts() {
        return this.contactDao.getContacts();
    }

    @Transactional
    @Override
    public Contact createContact(Contact contact) {
        return contactDao.createContact(contact);
    }

    @Override
    public List<Contact> getContacts(final String searchQuery) {
        return contactDao.getContacts(searchQuery);
    }
    
    @Override
    public Contact getContact(final int id){
        return contactDao.getContact(id);
    }
}
