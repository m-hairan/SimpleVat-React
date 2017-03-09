package com.simplevat.controller.contact;

import com.simplevat.entity.Contact;
import com.simplevat.service.ContactService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * Created by mohsinh on 3/9/2017.
 */

@Controller
@ManagedBean
@RequestScoped
public class ContactController implements Serializable {

    final static Logger logger = Logger.getLogger(ContactController.class);

    @Autowired
    DefaultListableBeanFactory beanFactory;

    @Autowired
    private ContactService contactService;

    @Getter
    @Setter
    private Contact contact;

    @PostConstruct
    public void init() {
        contact = new Contact();
    }

    public String createNewContact()
    {
        logger.debug("Creating contact");
        this.contactService.createContact(contact);

        ContactListController contactListController = (ContactListController) beanFactory.getBean("contactListController");
        logger.debug("contactListController :"+contactListController);
        contactListController.getContacts().add(contact);
        logger.debug("Created contact :"+contact.getContactId() +" Name :"+contact.getFirstName());
        this.init();
        return "/pages/secure/contact/contacts.xhtml";
    }


}
