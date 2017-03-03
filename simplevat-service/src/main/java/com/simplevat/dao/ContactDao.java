package com.simplevat.dao;

import com.simplevat.entity.Contact;

import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
public interface ContactDao {

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs);
}
