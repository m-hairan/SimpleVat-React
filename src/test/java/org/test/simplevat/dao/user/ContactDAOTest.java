package org.test.simplevat.dao.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.simplevat.dao.user.ContactDao;
import org.simplevat.domain.user.Contact;
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
public class ContactDAOTest {

    @Qualifier("contactDaoImpl")
    @Autowired
    private ContactDao contactDao;

    @Test
    public void testGetContacts(){

        List<Contact> contacts = contactDao.getContacts();
        Iterator<Contact> contactIt = contacts.iterator();

        while(contactIt.hasNext())
        {
            Contact contact = contactIt.next();
            System.out.println("Name :" + contact.contactFirstName + " " + contact.contactLastName);
        }


        Assert.assertTrue(contacts.size() == 2);
    }
}
