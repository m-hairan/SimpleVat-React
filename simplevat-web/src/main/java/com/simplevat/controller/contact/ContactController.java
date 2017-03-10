package com.simplevat.controller.contact;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Autowired
    private CountryService countryService;

    @Getter
    @Setter
    private List<Country> countries;

    @Getter
    @Setter
    private Contact contact;

    @PostConstruct
    public void init() {
        contact = new Contact();

        countries = countryService.getCountries();
        logger.debug("Loaded Countries :"+countries.size());
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


    public List<String> completeCountry(String countryStr)
    {
        List<String> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();

        logger.debug(" Size :"+countries.size());

        while (countryIterator.hasNext())
        {
            Country country = countryIterator.next();
            if(country.getCountryName() != null &&
                        !country.getCountryName().isEmpty() &&
                            country.getCountryName().toUpperCase().contains(countryStr.toUpperCase()))
            {
                countrySuggestion.add(country.getCountryName()+" - ("+country.getIsoAlpha3Code()+")");
            } else if(country.getIsoAlpha3Code() != null &&
                    !country.getIsoAlpha3Code().isEmpty() &&
                    country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase()))
            {
                countrySuggestion.add(country.getCountryName()+" - ("+country.getIsoAlpha3Code()+")");
            }
        }

        logger.debug(" Size :"+countrySuggestion.size());

        return countrySuggestion;
    }


    public List<Country> completeCountryNew(String countryStr)
    {
        List<Country> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();

        logger.debug(" Size :"+countries.size());

        while (countryIterator.hasNext())
        {
            Country country = countryIterator.next();
            if(country.getCountryName() != null &&
                    !country.getCountryName().isEmpty() &&
                    country.getCountryName().toUpperCase().contains(countryStr.toUpperCase()))
            {
                countrySuggestion.add(country);
            } else if(country.getIsoAlpha3Code() != null &&
                    !country.getIsoAlpha3Code().isEmpty() &&
                    country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase()))
            {
                countrySuggestion.add(country);
            }
        }

        logger.debug(" Size :"+countrySuggestion.size());

        return countrySuggestion;
    }



}
