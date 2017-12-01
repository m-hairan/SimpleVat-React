package com.simplevat.web.project.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.ProjectService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.contact.model.ContactModel;
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

    public ProjectController() {
        super(ModuleName.PROJECT_MODULE);
    }

    @PostConstruct
    public void init() {
        try {
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
            }
            this.languages = languageService.getLanguages();
            this.currencies = currencyService.getCurrencies();
        } catch (Exception ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery,ContactTypeConstant.CUSTOMER);
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
        // projectService.update(selectedProject);
        if (selectedProject.getProjectId() != null && selectedProject.getProjectId() > 0) {
            projectService.update(selectedProject);
        } else {
            projectService.persist(selectedProject);
        }
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Project saved successfully"));
        return "projects.xhtml?faces-redirect=true";
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

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {
        Currency defaultCurrency = currencyService.getDefaultCurrency();

        final Contact contact = new Contact();

        contact.setBillingEmail(contactModel.getEmail());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setEmail(contactModel.getEmail());
        contact.setFirstName(contactModel.getFirstName());
        contact.setLastName(contactModel.getLastName());
        contact.setOrganization(contactModel.getOrganization());
        contact.setCreatedBy(1);
        contact.setCurrency(defaultCurrency);
        contact.setContactType(ContactTypeConstant.CUSTOMER);

        contactModel = new ContactModel();

        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
        if (contact.getContactId() != null) {
            contactService.update(contact);
        } else {
            contactService.persist(contact);
        }

        selectedProject.setContact(contact);

    }

}
