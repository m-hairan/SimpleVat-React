package com.simplevat.test.dao;

import com.simplevat.entity.Contact;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
public class TestContactDao extends BaseManagerTest {

    @Test
    public void testGetContacts()
    {
        List<Contact> contacts = this.contactDao.getContacts(new Integer(1), new Integer(16));

        Iterator<Contact> contactIterator = contacts.iterator();
        while(contactIterator.hasNext())
        {
            Contact contact = contactIterator.next();
            System.out.println(contact.getFirstName()+" "+contact.getLastName());
        }
    }
}
