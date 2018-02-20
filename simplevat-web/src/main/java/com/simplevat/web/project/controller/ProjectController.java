package com.simplevat.web.project.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Project;
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TitleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.contact.controller.ContactHelper;
import com.simplevat.web.contact.controller.ContactUtil;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.contact.model.ContactType;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Controller;

/**
 * Created by Utkarsh Bhavsar on 25/03/17.
 */
@Controller
@SpringScopeView
public class ProjectController extends BaseController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ContactService contactService;

    @Getter
    @Setter
    private Project selectedProject;

    @Getter
    @Setter
    private List<Language> languages;

    @Getter
    @Setter
    private List<Currency> currencies;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Getter
    private Company company;

    @Autowired
    private TitleService titleService;

    @Getter
    private List<Title> titles = new ArrayList<>();

    @Getter
    private List<Country> countries = new ArrayList<>();

    public ProjectController() {
        super(ModuleName.PROJECT_MODULE);
    }

    @PostConstruct
    public void init() {
        try {
            company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
            selectedProject = new Project();
            Object objProjectId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedProjectId");
            if (objProjectId != null) {
                selectedProject = projectService.findByPK(Integer.parseInt(objProjectId.toString()));
            } else {
                Currency defaultCurrency = currencyService.getDefaultCurrency();
                if (defaultCurrency != null) {
                    this.selectedProject.setCurrency(defaultCurrency);
                }
                contactModel = new ContactModel();
                setDefaultLanguage();
            }
            this.languages = languageService.getLanguages();
            this.currencies = currencyService.getCurrencies();
            titles = titleService.getTitles();
            countries = countryService.getCountries();
            currencies = currencyService.getCurrencies();
            setDefaultCountry();
            setDefaultContactCurrency();

        } catch (Exception ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Title> completeTitle(String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();

        Iterator<Title> titleIterator = this.titles.iterator();

        while (titleIterator.hasNext()) {
            Title title = titleIterator.next();
            if (title.getTitleDescription() != null
                    && !title.getTitleDescription().isEmpty()
                    && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                titleSuggestion.add(title);
            }
        }

        return titleSuggestion;
    }

    public List<ContactType> completeContactType() {
        return ContactUtil.contactTypeList();
    }

    public List<Country> completeCountry(String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();

        Iterator<Country> countryIterator = this.countries.iterator();

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

        return countrySuggestion;
    }

    public List<Currency> completeCurrency(String currencyStr) {
        List<Currency> currencySuggestion = new ArrayList<>();
        Iterator<Currency> currencyIterator = this.currencies.iterator();

        while (currencyIterator.hasNext()) {
            Currency currency = currencyIterator.next();
            if (currency.getCurrencyName() != null
                    && !currency.getCurrencyName().isEmpty()
                    && currency.getCurrencyName().toUpperCase().contains(currencyStr.toUpperCase())) {

                currencySuggestion.add(currency);
            } else if (currency.getCurrencyDescription() != null
                    && !currency.getCurrencyDescription().isEmpty()
                    && currency.getCurrencyDescription().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);

            } else if (currency.getCurrencyIsoCode() != null
                    && !currency.getCurrencyIsoCode().isEmpty()
                    && currency.getCurrencyIsoCode().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);

            }
        }

        return currencySuggestion;
    }

    private void setDefaultCountry() {
        Country defaultCountry = company.getCompanyCountryCode();
        if (defaultCountry != null) {
            contactModel.setCountry(defaultCountry);
        }
    }

    private void setDefaultContactCurrency() {
        Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery, ContactTypeConstant.CUSTOMER);
    }

    private List<Project> getProjectFromCriteria() throws Exception {

        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

    public List<Project> projects(final String searchQuery) {
        try {
            ProjectCriteria criteria = new ProjectCriteria();
            criteria.setActive(Boolean.TRUE);
            if (searchQuery != null && !searchQuery.isEmpty()) {
                criteria.setProjectName(searchQuery);
            }
            return projectService.getProjectsByCriteria(criteria);
        } catch (Exception ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public String saveProject() throws Exception {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedProject.setCreatedBy(loggedInUser.getUserId());
        selectedProject.setCreatedDate(LocalDateTime.now());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (selectedProject.getProjectId() != null && selectedProject.getProjectId() > 0) {
            projectService.update(selectedProject);
            context.addMessage(null, new FacesMessage("Successful", "Project updated successfully"));
        } else {
            projectService.persist(selectedProject);
            context.addMessage(null, new FacesMessage("Successful", "Project saved successfully"));
        }
        init();

        return "projects.xhtml?faces-redirect=true";
    }

    public void saveAndAddMoreProject() throws Exception {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedProject.setCreatedBy(loggedInUser.getUserId());
        selectedProject.setCreatedDate(LocalDateTime.now());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (selectedProject.getProjectId() != null && selectedProject.getProjectId() > 0) {
            projectService.update(selectedProject);
            context.addMessage(null, new FacesMessage("Successful", "Project updated successfully"));
        } else {
            projectService.persist(selectedProject);
            context.addMessage(null, new FacesMessage("Successful", "Project saved successfully"));
        }
        init();

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

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {

        Contact contact = new Contact();
        ContactHelper contactHelper = new ContactHelper();
        User loggedInUser = FacesUtil.getLoggedInUser();
        contact = contactHelper.getContact(contactModel);
        contact.setCreatedBy(loggedInUser.getUserId());
        contact.setCreatedDate(LocalDateTime.now());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setContactType(ContactTypeConstant.CUSTOMER);
        if (contact.getContactId() != null && contact.getContactId() > 0) {
            this.contactService.update(contact);
        } else {

            this.contactService.persist(contact);
        }

        selectedProject.setContact(contact);

        RequestContext.getCurrentInstance().execute("PF('add_contact_popup').hide();");
        initCreateContact();
    }

    private void setDefaultLanguage() {
        Language defaultLanguage = languageService.getDefaultLanguage();
        if (defaultLanguage != null) {
            selectedProject.setInvoiceLanguageCode(defaultLanguage);
        }
    }
}
