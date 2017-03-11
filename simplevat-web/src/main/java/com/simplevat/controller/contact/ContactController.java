package com.simplevat.controller.contact;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mohsinh on 3/9/2017.
 */

@Controller
@ManagedBean(name = "contactController")
@RequestScoped
public class ContactController implements Serializable {

    final static Logger logger = Logger.getLogger(ContactController.class);

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    private ContactListController contactListController;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CountryService countryService;


    @Autowired
    private LanguageService languageService;

    @Autowired
    private CurrencyService currencyService;

    @Getter
    @Setter
    private List<Country> countries;

    @Getter
    @Setter
    private List<Language> languages;

    @Getter
    @Setter
    private List<Currency> currencies;

    @Getter
    @Setter
    private Contact contact;

    @PostConstruct
    public void init() {
        contact = new Contact();

        countries = countryService.getCountries();
        languages = languageService.getLanguages();
        currencies = currencyService.getCurrencies();
        contactListController = (ContactListController) beanFactory.getBean("contactListController");

        logger.debug("Loaded Countries :"+countries.size());
    }

    public String createNewContact()
    {
        logger.debug("Creating contact");
        this.contactService.createContact(contact);

        logger.debug("contactListController :"+contactListController);
        contactListController.getContacts().add(contact);
        logger.debug("Created contact :"+contact.getContactId() +" Name :"+contact.getFirstName());
        this.init();
        return "/pages/secure/contact/contacts.xhtml";
    }


    public List<Country> completeCountry(String countryStr)
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

    public List<Language> completeLanguage(String languageStr)
    {
        List<Language> languageSuggestion = new ArrayList<>();
        Iterator<Language> languageIterator = this.languages.iterator();

        logger.debug(" Size :"+languages.size());

        while (languageIterator.hasNext())
        {
            Language language = languageIterator.next();
            if(language.getLanguageName() != null &&
                    !language.getLanguageName().isEmpty() &&
                    language.getLanguageName().toUpperCase().contains(languageStr.toUpperCase()))
            {
                logger.debug(" Language :"+language.getLanguageDescription());
                languageSuggestion.add(language);
            } else if(language.getLanguageDescription()     != null &&
                    !language.getLanguageDescription().isEmpty() &&
                    language.getLanguageDescription().toUpperCase().contains(languageStr.toUpperCase()))
            {
                languageSuggestion.add(language);
                logger.debug(" Language :"+language.getLanguageDescription());
            }
        }

        logger.debug(" Size :"+languageSuggestion.size());

        return languageSuggestion;
    }


    public List<Currency> completeCurrency(String currencyStr)
    {
        List<Currency> currencySuggestion = new ArrayList<>();
        Iterator<Currency> currencyIterator = this.currencies.iterator();

        logger.debug(" Size :"+languages.size());

        while (currencyIterator.hasNext())
        {
            Currency currency = currencyIterator.next();
            if(currency.getCurrencyName() != null &&
                    !currency.getCurrencyName().isEmpty() &&
                    currency.getCurrencyName().toUpperCase().contains(currencyStr.toUpperCase()))
            {
                logger.debug(" Language :"+currency.getCurrencyDescription());
                currencySuggestion.add(currency);
            } else if(currency.getCurrencyDescription()     != null &&
                    !currency.getCurrencyDescription().isEmpty() &&
                    currency.getCurrencyDescription().toUpperCase().contains(currencyStr.toUpperCase()))
            {
                currencySuggestion.add(currency);
                logger.debug(" Language :"+currency.getCurrencyDescription());
            } else if(currency.getCurrencyIsoCode()     != null &&
                    !currency.getCurrencyIsoCode().isEmpty() &&
                    currency.getCurrencyIsoCode().toUpperCase().contains(currencyStr.toUpperCase()))
            {
                currencySuggestion.add(currency);
                logger.debug(" Language :"+currency.getCurrencyIsoCode());
            }
        }

        logger.debug(" Size :"+currencySuggestion.size());

        return currencySuggestion;
    }


    public String redirectToContactList()
    {
        logger.debug("Redirecting to create new contacts page");
        return "/pages/secure/contact/contacts.xhtml";
    }

}
