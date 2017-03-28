package com.simplevat.controller.project;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Language;
import com.simplevat.entity.Project;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.ProjectService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Utkarsh Bhavsar on 25/03/17.
 */
@RequestScoped
@ManagedBean
@Component
public class ProjectController {

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
    private List<Project> projects;

    @Getter
    @Setter
    private Project selectedProject;

    @Getter
    @Setter
    private List<Language> languages;

    @Getter
    @Setter
    private List<Currency> currencies;

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery);
    }

    private List<Project> getProjectFromCriteria() throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        return projectService.getProjectsByCriteria(projectCriteria);
    }

    @PostConstruct
    public void init() throws Exception {
        this.projects = getProjectFromCriteria();
        this.languages = languageService.getLanguages();
        this.currencies = currencyService.getCurrencies();
    }


    public String redirectToCreateProject() {
        this.selectedProject = new Project();
        return "/pages/secure/project/project.xhtml";
    }


    public String redirectToEditProject(Project project) throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setProjectId(project.getProjectId());
        List<Project> projects = projectService.getProjectsByCriteria(projectCriteria);
        if (CollectionUtils.isEmpty(projects)) {
            throw new Exception("Invalid Project Id");
        }
        this.selectedProject = projects.get(0);
        return "/pages/secure/project/project.xhtml";
    }


    public void deleteProject(Project project) throws Exception {
        project.setDeleteFlag('Y');
        projectService.updateOrCreateProject(project);
    }

    public String saveProject() throws Exception {
        projectService.updateOrCreateProject(selectedProject);
        init();
        return "/pages/secure/project/projects.xhtml";
    }


    public List<Language> completeLanguage(String languageStr) {
        List<Language> languageSuggestion = new ArrayList<>();
        Iterator<Language> languageIterator = this.languages.iterator();

        LOGGER.debug(" Size :" + languages.size());

        while (languageIterator.hasNext()) {
            Language language = languageIterator.next();
            if (language.getLanguageName() != null &&
                    !language.getLanguageName().isEmpty() &&
                    language.getLanguageName().toUpperCase().contains(languageStr.toUpperCase())) {
                LOGGER.debug(" Language :" + language.getLanguageDescription());
                languageSuggestion.add(language);
            } else if (language.getLanguageDescription() != null &&
                    !language.getLanguageDescription().isEmpty() &&
                    language.getLanguageDescription().toUpperCase().contains(languageStr.toUpperCase())) {
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
            if (currency.getCurrencyName() != null &&
                    !currency.getCurrencyName().isEmpty() &&
                    currency.getCurrencyName().toUpperCase().contains(currencyStr.toUpperCase())) {
                LOGGER.debug(" Language :" + currency.getCurrencyDescription());
                currencySuggestion.add(currency);
            } else if (currency.getCurrencyDescription() != null &&
                    !currency.getCurrencyDescription().isEmpty() &&
                    currency.getCurrencyDescription().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
                LOGGER.debug(" Language :" + currency.getCurrencyDescription());
            } else if (currency.getCurrencyIsoCode() != null &&
                    !currency.getCurrencyIsoCode().isEmpty() &&
                    currency.getCurrencyIsoCode().toUpperCase().contains(currencyStr.toUpperCase())) {
                currencySuggestion.add(currency);
                LOGGER.debug(" Language :" + currency.getCurrencyIsoCode());
            }
        }

        LOGGER.debug(" Size :" + currencySuggestion.size());

        return currencySuggestion;
    }



}
