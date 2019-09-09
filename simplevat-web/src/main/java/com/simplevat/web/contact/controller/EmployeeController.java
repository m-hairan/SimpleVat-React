package com.simplevat.web.contact.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.*;
import com.simplevat.service.*;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.constant.RoleCode;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.utils.FacesUtil;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 * Created by mohsinh on 3/9/2017.
 */
@Controller
@SpringScopeView
public class EmployeeController extends BaseController implements Serializable {

    private static final long serialVersionUID = -6783876735681802047L;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

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
    private List<Country> countries = new ArrayList<>();

    @Getter
    private List<Language> languages = new ArrayList<>();

    @Getter
    private List<Currency> currencies = new ArrayList<>();

    @Getter
    private List<Title> titles = new ArrayList<>();

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    @Setter
    private Contact selectedContact;

    @Autowired
    private CompanyService companyService;

    @Getter
    @Setter
    private Company company;

    @Autowired
    private UserServiceNew userServiceNew;

    @Getter
    @Setter
    private ContactHelper contactHelper;

    @Getter
    @Setter
    private Date today = new Date();

    @Autowired
    private RoleService roleService;

    @Getter
    @Setter
    private String type = null;

    public EmployeeController() {
        super(ModuleName.EMPLOYEE_MODULE);
    }

    @PostConstruct
    public void init() {
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        selectedContact = new Contact();
        contactModel = new ContactModel();
        contactHelper = new ContactHelper();
        Object objContactId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedContactId");
        System.out.println("selected : :" + objContactId);
        if (objContactId != null) {
            Contact contact = contactService.findByPK(Integer.parseInt(objContactId.toString()));
            contactModel = contactHelper.getContactModel(contact);
            titles = titleService.getTitles();
        } else {
            contactModel = new ContactModel();
            //contactService.
            if (contactService.getLastContact() != null) {
                contactModel.setContactCode((contactService.getLastContact().getContactId() + 1));
            } else {
                contactModel.setContactCode(1001);
            }
            setDefaultCurrency();
            setDefaultCountry();
            setDefaultLanguage();
            titles = titleService.getTitles();
            LOGGER.debug("Loaded Countries :" + countries.size());
        }
        currencies = currencyService.getCurrencies();
        countries = countryService.getCountries();
        languages = languageService.getLanguages();

    }

    public List<ContactType> completeContactType() {
        return ContactUtil.contactTypeList();
    }

    private void setDefaultCurrency() {

        Currency defaultCurrency = company.getCompanyCountryCode() != null ? company.getCompanyCountryCode().getCurrencyCode() : null;
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
    }

    private void setDefaultCountry() {
        Country defaultCountry = company.getCompanyCountryCode();
        if (defaultCountry != null) {
            contactModel.setCountry(defaultCountry);
        }
    }

    public void changeType() {
        contactModel.setNonEmployeeUser(null);
    }

    private void setDefaultLanguage() {
        Language defaultLanguage = languageService.getDefaultLanguage();
        if (defaultLanguage != null) {
            contactModel.setLanguage(defaultLanguage);
        }
    }

    public String redirectToCreateContact() {
        contactModel = new ContactModel();
        contactModel.setCountry(countries.get(179));
        contactModel.setLanguage(languages.get(0));
        setDefaultCurrency();
        LOGGER.debug("Redirecting to create new contact page");
        return "contact?faces-redirect=true";
    }

    public void updateOrganization() {
        if (contactModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
            contactModel.setOrganization(company.getCompanyName());
        }
    }

    public void createOrUpdateContact() throws IOException {
        Optional<User> optionalUser = userServiceNew.getUserByEmail(contactModel.getEmail());
        String message = "";
        Boolean isPresent = false;
        User user = null;
        if (contactModel.getNonEmployeeUser() != null) {
            isPresent = false;
            user = contactModel.getNonEmployeeUser();
        } else {
            isPresent = optionalUser.isPresent();
            user = new User();
        }
        if (!isPresent) {
            User loggedInUser = FacesUtil.getLoggedInUser();

            selectedContact = contactHelper.getContact(contactModel);
            if (contactModel.getNonEmployeeUser() != null) {
                selectedContact.setEmail(contactModel.getNonEmployeeUser().getUserEmail());
            }
            selectedContact.setContactType(ContactTypeConstant.EMPLOYEE);
            if (selectedContact.getContactId() != null && selectedContact.getContactId() > 0) {
                this.contactService.update(selectedContact);
            } else {
                selectedContact.setCreatedBy(loggedInUser.getUserId());
                this.contactService.persist(selectedContact);
            }
            setUserData(user, selectedContact, loggedInUser, contactModel);
            if (contactModel.getNonEmployeeUser() != null) {
                userServiceNew.update(user);
            } else {
                userServiceNew.persist(user);
            }
            contactModel = new ContactModel();
            message = "Employee saved successfully ";
        } else {
            message = "Email already present";
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", message));
        //return "list?faces-redirect=true";
    }

    public void setUserData(User user, Contact contact, User loggedInUser, ContactModel contactModel) {
        if (contactModel.getNonEmployeeUser() == null) {
            user.setCompany(loggedInUser.getCompany());
            user.setCreatedBy(loggedInUser.getUserId());
            user.setCreatedDate(LocalDateTime.now());
            user.setDeleteFlag(Boolean.FALSE);
            user.setIsActive(Boolean.TRUE);
            user.setUserEmail(contactModel.getEmail());
            user.setPassword((new BCryptPasswordEncoder()).encode(contactModel.getPassword()));
        }
        user.setDateOfBirth(Instant.ofEpochMilli(contactModel.getDob() != null ? contactModel.getDob().getTime() : (new Date()).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        user.setEmployeeId(contact);
        user.setFirstName(contact.getFirstName());
        user.setLastName(contact.getLastName());
        Role role = roleService.findByPK(RoleCode.EMPLOYEE.getCode());
        user.setRole(role);

    }

    public void createOrUpdateAndAddMore() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedContact = contactHelper.getContact(contactModel);
        if (selectedContact.getContactId() != null && selectedContact.getContactId() > 0) {
            this.contactService.update(selectedContact);
        } else {
            selectedContact.setCreatedBy(loggedInUser.getUserId());
            this.contactService.persist(selectedContact);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Contact saved successfully"));
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

    public List<User> completeUser(String userStr) {
        List<User> users = userServiceNew.getAllUserNotEmployee();
        return users;
    }

    public void updateUserDetailOnFront() {
        if (contactModel.getNonEmployeeUser() != null) {
            User nonEmployeeUser = contactModel.getNonEmployeeUser();
            contactModel.setFirstName(nonEmployeeUser.getFirstName());
            contactModel.setLastName(nonEmployeeUser.getLastName());
            if (nonEmployeeUser.getDateOfBirth() != null) {
                contactModel.setDob(Date.from(nonEmployeeUser.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()));
            }
            contactModel.setEmail(nonEmployeeUser.getUserEmail());
        }
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
        return "list?faces-redirect=true";
    }

    public String redirectToEditContact(int contactId) throws IOException {
//        contactModel = new ContactModel();
//        BeanUtils.copyProperties(contact, contactModel);
//        contactModel.setEmailAddress(contact.getEmail());
//        contactModel.setOrganization(contact.getOrganization());
//        LOGGER.debug("Redirecting to create new contact page");
//        if(contact.getContactId() == null){
//            contactService.persist(contact);
//        }else{
//            contactService.update(contact, contact.getContactId());
//        }
        System.out.println("selectedContact.getContactId() :" + contactId);
        // System.out.println("id___________-----"+selectedContact.getContactId());
        return "contact?faces-redirect=true&selectedContactId=" + contactId;
    }

    public void updateBillingEmail() {
        contactModel.setBillingEmail(contactModel.getEmail());
    }

    public void countryOnSelectListner() {
        contactModel.setCurrency(contactModel.getCountry().getCurrencyCode());
    }
}
