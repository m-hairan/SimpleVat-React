package com.simplevat.controller.contact;

import com.simplevat.contact.model.ContactModel;
import com.simplevat.entity.*;
import com.simplevat.service.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by mohsinh on 3/9/2017.
 */
@Controller
@ManagedBean(name = "contactController")
@RequestScoped
public class ContactController implements Serializable {

    private static final long serialVersionUID = -6783876735681802047L;

    private final static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private TitleService titleService;

    @Getter
    private List<Country> countries;

    @Getter
    private List<Language> languages;

    @Getter
    private List<Currency> currencies;

    @Getter
    private List<Title> titles;

    @Getter
    private ContactModel contactModel;

    @PostConstruct
    public void init() {
        contactModel = new ContactModel();

        countries = countryService.getCountries();
        languages = languageService.getLanguages();
        currencies = currencyService.getCurrencies();
        titles = titleService.getTitles();

        contactModel.setCountry(countries.get(179));
        contactModel.setLanguage(languages.get(0));
        contactModel.setCurrency(currencies.get(149));
        LOGGER.debug("Loaded Countries :" + countries.size());
    }

    public String redirectToCreateContact() {
        contactModel = new ContactModel();
        contactModel.setCountry(countries.get(179));
        contactModel.setLanguage(languages.get(0));
        contactModel.setCurrency(currencies.get(149));
        LOGGER.debug("Redirecting to create new contact page");
        return "/pages/secure/contact/contact.xhtml";
    }

    public void createOrUpdateContact() throws IOException {
        LOGGER.debug("Creating contact");
        final Contact contact = new Contact();
        BeanUtils.copyProperties(contactModel, contact);
        contact.setOrganization(contactModel.getOrganizationName());
        contact.setEmail(contactModel.getEmailAddress());
        contact.setCreatedBy(1);
        this.contactService.createOrUpdateContact(contact);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (contactModel.getContactId() > 0) {
            context.addMessage(null, new FacesMessage("Contact Updated SuccessFully"));
        } else {
            context.addMessage(null, new FacesMessage("Contact Created SuccessFully"));
        }

        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("contacts.xhtml?faces-redirect=true");
    }

    public void createOrUpdateAndAddMore() {
        final Contact contact = new Contact();
        BeanUtils.copyProperties(contactModel, contact);
        contact.setOrganization(contactModel.getOrganizationName());
        contact.setEmail(contactModel.getEmailAddress());
        contact.setCreatedBy(1);
        this.contactService.createOrUpdateContact(contact);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (contactModel.getContactId() > 0) {
            context.addMessage(null, new FacesMessage("Contact Updated SuccessFully"));
        } else {
            context.addMessage(null, new FacesMessage("Contact Created SuccessFully"));
        }
        init();
    }

    public List<Title> completeTitle(String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();
        Iterator<Title> titleIterator = this.titles.iterator();

        LOGGER.debug(" Size :" + titles.size());

        while (titleIterator.hasNext()) {
            Title title = titleIterator.next();
            if (title.getTitleDescription() != null
                    && !title.getTitleDescription().isEmpty()
                    && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                titleSuggestion.add(title);
            }
        }

        LOGGER.debug(" Size :" + titleSuggestion.size());

        return titleSuggestion;
    }

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        Iterator<Country> countryIterator = this.countries.iterator();

        LOGGER.debug(" Size :" + countries.size());

        while (countryIterator.hasNext()) {
            Country country = countryIterator.next();
            if (country.getCountryName() != null
                    && !country.getCountryName().isEmpty()
                    && country.getCountryName().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            } else if (country.getIsoAlpha3Code() != null
                    && !country.getIsoAlpha3Code().isEmpty()
                    && country.getIsoAlpha3Code().toUpperCase().contains(countryStr.toUpperCase())) {
                countrySuggestion.add(country);
            }
        }

        LOGGER.debug(" Size :" + countrySuggestion.size());

        return countrySuggestion;
    }

    public List<Language> completeLanguage(String languageStr) {
        List<Language> languageSuggestion = new ArrayList<>();
        Iterator<Language> languageIterator = this.languages.iterator();

        LOGGER.debug(" Size :" + languages.size());

        while (languageIterator.hasNext()) {
            Language language = languageIterator.next();
            if (language.getLanguageName() != null
                    && !language.getLanguageName().isEmpty()
                    && language.getLanguageName().toUpperCase().contains(languageStr.toUpperCase())) {
                LOGGER.debug(" Language :" + language.getLanguageDescription());
                languageSuggestion.add(language);
            } else if (language.getLanguageDescription() != null
                    && !language.getLanguageDescription().isEmpty()
                    && language.getLanguageDescription().toUpperCase().contains(languageStr.toUpperCase())) {
                languageSuggestion.add(language);
                LOGGER.debug(" Language :" + language.getLanguageDescription());
            }
        }

        LOGGER.debug(" Size :" + languageSuggestion.size());

        return languageSuggestion;
    }

    public List<Currency> completeCurrency(String currencyStr) {
        List<Currency> currencySuggestion = new ArrayList<>();
        Iterator<Currency> currencyIterator = this.currencies.iterator();

        LOGGER.debug(" Size :" + languages.size());

        while (currencyIterator.hasNext()) {
            Currency currency = currencyIterator.next();
            if (currency.getCurrencyName() != null
                    && !currency.getCurrencyName().isEmpty()
                    && currency.getCurrencyName().toUpperCase().contains(currencyStr.toUpperCase())) {
                LOGGER.debug(" Language :" + currency.getCurrencyDescription());
                currencySuggestion.add(currency);
            } else if (currency.getCurrencyDescription() != null
                    && !currency.getCurrencyDescription().isEmpty()
                    && currency.getCurrencyDescription().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
                LOGGER.debug(" Language :" + currency.getCurrencyDescription());
            } else if (currency.getCurrencyIsoCode() != null
                    && !currency.getCurrencyIsoCode().isEmpty()
                    && currency.getCurrencyIsoCode().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
                LOGGER.debug(" Language :" + currency.getCurrencyIsoCode());
            }
        }

        LOGGER.debug(" Size :" + currencySuggestion.size());

        return currencySuggestion;
    }

    public String redirectToContactList() {
        LOGGER.debug("Redirecting to create new contacts page");
        return "/pages/secure/contact/contacts.xhtml";
    }

    public void redirectToEditContact(Contact contact) throws IOException {
        contactModel = new ContactModel();
        BeanUtils.copyProperties(contact, contactModel);
        contactModel.setEmailAddress(contact.getEmail());
        contactModel.setOrganizationName(contact.getOrganization());
        LOGGER.debug("Redirecting to create new contact page");
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("contact.xhtml");
    }

    public void updateBillingEmail() {
        contactModel.setBillingEmail(contactModel.getEmailAddress());
    }
}
