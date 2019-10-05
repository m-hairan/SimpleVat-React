/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.projectcontroller;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Project;
import com.simplevat.entity.Title;
import com.simplevat.entity.User;
import com.simplevat.rest.contactController.ContactHelper;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TitleService;
import com.simplevat.constant.ContactTypeConstant;
import com.simplevat.contact.model.ContactModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sonu
 */
@RestController
@RequestMapping(value = "/rest/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/getprojectbycriteria")
    private ResponseEntity<List<Project>> getProjects() throws Exception {
        ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.setActive(Boolean.TRUE);
        List<Project> projects = projectService.getProjectsByCriteria(projectCriteria);

        if (projects == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteproject")
    private ResponseEntity deleteProject(@RequestParam(value = "id") Integer id) throws Exception {
        try {
            Project project = projectService.findByPK(id);

            if (project == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);

            } else {
                project.setDeleteFlag(Boolean.TRUE);
                projectService.update(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/editproject")
    private ResponseEntity<Project> editProject(@RequestParam(value = "id") Integer id) throws Exception {
        Project project = projectService.findByPK(id);

        if (project == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping(value = "/getcontactbyName")
    private ResponseEntity<List<Contact>> getContacts(@RequestParam(value = "searchQuery") String searchQuery) {
        List<Contact> contact = contactService.getContacts(searchQuery, ContactTypeConstant.CUSTOMER);
        if (contact == null && contact.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @GetMapping(value = "/getcurrenncy")
    private ResponseEntity<List<Currency>> getCurrency(@RequestParam(value = "currencyStr") String currencyStr) {
        List<Currency> currencySuggestion = new ArrayList<>();
        List<Currency> currencies = currencyService.getCurrencies();

        Iterator<Currency> currencyIterator = currencies.iterator();
        if (currencyIterator == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
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

            return new ResponseEntity<>(currencySuggestion, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/gettitle")
    private ResponseEntity<List<Title>> completeTitle(@RequestParam(value = "titleStr") String titleStr) {
        List<Title> titleSuggestion = new ArrayList<>();
        List<Title> titles = titleService.getTitles();
        Iterator<Title> titleIterator = titles.iterator();
        if (titleIterator == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        } else {
            while (titleIterator.hasNext()) {
                Title title = titleIterator.next();
                if (title.getTitleDescription() != null
                        && !title.getTitleDescription().isEmpty()
                        && title.getTitleDescription().toUpperCase().contains(titleStr.toUpperCase())) {
                    titleSuggestion.add(title);
                }
            }

            return new ResponseEntity<>(titleSuggestion, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getcountry")
    private ResponseEntity<List<Country>> getCountry(@RequestParam(value = "countryStr") String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        List<Country> countries = countryService.getCountries();

        Iterator<Country> countryIterator = countries.iterator();
        if (countryIterator == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        } else {

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
            return new ResponseEntity<>(countrySuggestion, HttpStatus.OK);

        }
    }

    @PostMapping(value = "/saveprojectcontact")
    private ResponseEntity createContact(@RequestBody ContactModel contactModel, @RequestParam(value = "id") Integer id) {

        Contact contact = new Contact();
        ContactHelper contactHelper = new ContactHelper();
        contact = contactHelper.getContact(contactModel);
        contact.setCreatedBy(id);
        contact.setCreatedDate(LocalDateTime.now());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setContactType(ContactTypeConstant.CUSTOMER);
        if (contact.getContactId() != null && contact.getContactId() > 0) {
            this.contactService.update(contact);
        } else {

            this.contactService.persist(contact);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/saveproject")
    private ResponseEntity saveProject(@RequestBody Project project, @RequestParam(value = "id") Integer id) throws Exception {
        project.setCreatedBy(id);
        project.setCreatedDate(LocalDateTime.now());
        if (project.getProjectId() != null && project.getProjectId() > 0) {
            projectService.update(project);
        } else {
            projectService.persist(project);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
