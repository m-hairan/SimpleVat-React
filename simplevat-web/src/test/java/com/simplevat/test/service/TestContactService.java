package com.simplevat.test.service;

import com.simplevat.entity.Contact;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsin on 3/4/2017.
 */

public class TestContactService extends BaseManagerTest {

    @Test
    @Transactional
    public void testGetContacts()
    {
        List<Contact> contacts = this.contactService.getContacts(new Integer(0), new Integer(20));

        System.out.println(" Size of contacts :" + contacts.size());

        Iterator<Contact> contactIterator = contacts.iterator();
        while(contactIterator.hasNext())
        {
            Contact contact = contactIterator.next();
            System.out.println(contact.getContactId()+" - "+contact.getFirstName()+" "+contact.getLastName()+" Country :"+contact.getCountryByCountryCode().getCountryName());
        }
    }

    @Test
    @Transactional
    public void testCreateContact()
    {
        Contact contact = new Contact();
        contact.setFirstName("Mohsin");
        contact.setLastName("Hashmi");

        contactService.createContact(contact);

        System.out.println(contact.getContactId()+" - "+contact.getFirstName()+" "+contact.getLastName());
    }
}
