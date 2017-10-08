package com.simplevat.dao.impl;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ContactDao;
import com.simplevat.entity.Contact;
import com.simplevat.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
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
    public List<Contact> getContacts(final String searchQuery) {
        List<Contact> contacts = getEntityManager()
                .createNamedQuery("Contact.contactsByName", Contact.class)
                .setParameter("name", "%" + searchQuery + "%")
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

}
