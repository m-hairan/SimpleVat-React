package com.simplevat.service.impl;

import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */

@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactDao contactDao;

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs) {
        return contactDao.getContacts(pageIndex,noOfRecorgs);
    }
}
