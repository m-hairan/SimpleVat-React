package com.simplevat.test.service;

import com.simplevat.entity.Contact;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsin on 3/4/2017.
 */
public class TestContactService extends BaseManagerTest {

    @Test
    public void testGetContacts()
    {
        List<Contact> contacts = this.contactService.getContacts(new Integer(1), new Integer(20));

        Iterator<Contact> contactIterator = contacts.iterator();
        while(contactIterator.hasNext())
        {
            Contact contact = contactIterator.next();
            System.out.println(contact.getContactId()+" - "+contact.getFirstName()+" "+contact.getLastName());
        }
    }
}
