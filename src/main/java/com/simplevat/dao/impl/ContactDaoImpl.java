package com.simplevat.dao.impl;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import com.simplevat.entity.ContactView;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Created by mohsin on 3/3/2017.
 */
@Repository(value = "contactDao")
public class ContactDaoImpl extends AbstractDao<Integer, Contact> implements ContactDao {

    @Override
    public List<Contact> getContacts(Integer pageIndex, Integer noOfRecorgs) {
        List<Contact> contacts = getEntityManager().createNamedQuery("allContacts", Contact.class)
                .setMaxResults(noOfRecorgs)
                .setFirstResult(pageIndex * noOfRecorgs).getResultList();
        return contacts;
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts = this.executeNamedQuery("allContacts");
        //List<Contact> contacts = entityManager.createNamedQuery("allContacts", Contact.class).getResultList();
        return contacts;
    }

    @Override
    public List<Contact> getContacts(final String searchQuery, int contactType) {
        List<Contact> contacts = getEntityManager()
                .createNamedQuery("Contact.contactsByName", Contact.class)
                .setParameter("name", "%" + searchQuery + "%")
                .setParameter("contactType", contactType)
                .getResultList();
        return contacts;
    }

    @Override
    public Optional<Contact> getContactByEmail(String Email) {
        Query query = getEntityManager()
                .createNamedQuery("Contact.contactByEmail", Contact.class)
                .setParameter("email", Email);
        List resultList = query.getResultList();
        if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 1) {
            return Optional.of((Contact) resultList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<ContactView> getContactViewList() {
        TypedQuery<ContactView> query = getEntityManager().createQuery("SELECT c FROM ContactView c", ContactView.class);
        List<ContactView> contactViewList = query.getResultList();
        if (contactViewList != null && !contactViewList.isEmpty()) {
            return contactViewList;
        }
        return null;
    }

    @Override
    public Contact getLastContact() {
        TypedQuery<Contact> query = getEntityManager().createQuery("SELECT c FROM Contact c ORDER BY c.contactId DESC", Contact.class);
        List<Contact> contacts = query.getResultList();
        if (contacts != null && !contacts.isEmpty()) {
            return contacts.get(0);
        }
        return null;
    }

}
