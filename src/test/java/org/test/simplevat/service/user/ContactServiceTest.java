package org.test.simplevat.service.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.simplevat.domain.user.Contact;
import org.simplevat.service.user.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsin on 2/17/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class ContactServiceTest {

    @Qualifier("contactServiceImpl")
    @Autowired
    private ContactService contactService;

    @Test
    public void testGetContacts(){

        List<Contact> contacts = contactService.getContacts();
        Iterator<Contact> contactIt = contacts.iterator();

        while(contactIt.hasNext())
        {
            Contact contact = contactIt.next();
            System.out.println("Name :" + contact.getContactFirstName() + " " + contact.getContactLastName());
        }


        Assert.assertTrue(contacts.size() == 2);

    }
}
