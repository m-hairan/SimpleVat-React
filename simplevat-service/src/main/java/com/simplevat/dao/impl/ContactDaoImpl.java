package com.simplevat.dao.impl;

import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by mohsin on 3/3/2017.
 */
@Repository
public class ContactDaoImpl implements ContactDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs) {
        List<Contact> contacts = entityManager.createNamedQuery("allContacts", Contact.class)
                .setMaxResults(noOfRecorgs)
                .setFirstResult(pageIndex * noOfRecorgs).getResultList();
        return contacts;
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = entityManager.createNamedQuery("allContacts", Contact.class).getResultList();
        return contacts;
    }

    @Override
    public List<Contact> getContacts(final String searchQuery) {
        List<Contact> contacts = entityManager
                .createNamedQuery("Contact.contactsByName", Contact.class)
                .setParameter("name", "%" + searchQuery + "%")
                .getResultList();
        return contacts;
    }

    @Override
    public Contact createContact(Contact contact) {
        entityManager.persist(contact);
        return contact;
    }

    @Override
    public Contact getContact(int id) {
        return entityManager.find(Contact.class, id);
    }
}
