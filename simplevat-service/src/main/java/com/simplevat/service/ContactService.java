package com.simplevat.service;

import com.simplevat.entity.Contact;

import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
public interface ContactService {

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);
}
