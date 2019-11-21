/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.contactController;

import com.simplevat.bank.model.DeleteModel;
import com.simplevat.utils.ContactUtil;
import com.simplevat.helper.ContactHelper;
import com.simplevat.entity.Contact;
import com.simplevat.entity.ContactView;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.Title;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.LanguageService;
import com.simplevat.service.TitleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.constant.ContactTypeConstant;
import com.simplevat.contact.model.ContactModel;
import com.simplevat.contact.model.ContactType;
import com.simplevat.contact.model.ContactViewModel;
import com.simplevat.contact.model.CountryModel;
import java.io.Serializable;
import java.util.ArrayList;
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
@RequestMapping("/rest/contact")
public class ContactController implements Serializable {

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

    @Autowired
    private UserServiceNew userServiceNew;

    private ContactHelper contactHelper = new ContactHelper();

    private int totalEmployees;

    private int totalVendors;

    private int totalCustomers;

    private int totalContacts;

    @GetMapping("/contactlist")
    public ResponseEntity populateContactList() {
        List<ContactView> contactViewList = contactService.getContactViewList();

        List<ContactViewModel> contactList = new ArrayList<>();
        if (contactViewList != null) {
            for (ContactView contactView : contactViewList) {
                ContactViewModel contactViewModel = contactHelper.getContactViewModel(contactView);
                contactCountByType(contactViewModel);
                contactList.add(contactViewModel);
            }
        }

        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    private void contactCountByType(ContactViewModel contactViewModel) {
        totalEmployees = 0;
        totalCustomers = 0;
        totalVendors = 0;
        totalContacts = 0;
        if (contactViewModel.getContactType() != null) {
            if (contactViewModel.getContactType().getId() == ContactTypeConstant.EMPLOYEE) {
                totalEmployees++;
            } else if (contactViewModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                totalCustomers++;
            } else {
                totalVendors++;
            }
        }
        totalContacts++;
    }

    @GetMapping(value = "/contactvendorlist")
    public ResponseEntity populateContactVendorList() {
        List<ContactView> contactViewList = contactService.getContactViewList();
        List<ContactViewModel> contactList = new ArrayList<>();
        if (contactViewList != null) {
            for (ContactView contactView : contactViewList) {
                ContactViewModel contactViewModel = contactHelper.getContactViewModel(contactView);
                if (contactViewModel.getContactType().getId() == ContactTypeConstant.VENDOR) {
                    contactList.add(contactViewModel);
                }
            }
        }
        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    @GetMapping(value = "/contactcustomerlist")
    public ResponseEntity populateContactCustomerList() {
        List<ContactView> contactViews = contactService.getContactViewList();

        List<ContactViewModel> contactViewModels = new ArrayList<>();
        for (ContactView contactView : contactViews) {
            ContactViewModel contactViewModel = contactHelper.getContactViewModel(contactView);
            if (contactViewModel.getContactType().getId() == ContactTypeConstant.CUSTOMER) {
                contactViewModels.add(contactViewModel);
            }
        }
        return new ResponseEntity<>(contactViewModels, HttpStatus.OK);
    }

    @GetMapping(value = "/contacttype")
    public ResponseEntity getContactType() {
        try {
            List<ContactType> contactTypes = ContactUtil.contactTypeList();
            if (contactTypes != null && !contactTypes.isEmpty()) {
                return new ResponseEntity<>(contactTypes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/titlelist")
    public ResponseEntity titleList() {
        List<Title> titles = titleService.getTitles();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    @Deprecated
    @GetMapping(value = "/countrieslist")
    public ResponseEntity countryList() {
        ArrayList<CountryModel> countryModel = new ArrayList<>();
        List<Country> countrys = countryService.getCountries();
        for (Country country : countrys) {
            CountryModel countryModel1 = new CountryModel();
            countryModel1.setCountryCode(String.valueOf(country.getCountryCode()));
            countryModel1.setCountryDescription(country.getCountryDescription());
            countryModel1.setCountryFullName(country.getCountryDescription());
            countryModel1.setCountryName(country.getCountryName());
            countryModel1.setIsoAlpha3Code(country.getIsoAlpha3Code());
            countryModel.add(countryModel1);
        }
        if (!countryModel.isEmpty()) {
            return new ResponseEntity<>(countryModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Deprecated
    @GetMapping(value = "/currencieslist")
    public ResponseEntity CurrenciesList() {
        try {
            List<Currency> currencys = currencyService.getCurrencies();
            if (currencys != null && !currencys.isEmpty()) {
                return new ResponseEntity<>(currencys, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(currencys, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/savecontact")
    public ResponseEntity createOrUpdateContact(@RequestBody Contact contact, @RequestParam(value = "id") Integer id) {
        try {
            if (contact.getContactId() != null && contact.getContactId() > 0) {
                contactService.update(contact);
            } else {
                contact.setCreatedBy(1);
                contactService.persist(contact);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping(value = "/editcontact")
    public ResponseEntity editContact(@RequestParam(value = "id") Integer id) {
        Contact contact = contactService.findByPK(id);
        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ContactModel contactModel = contactHelper.getContactModel(contact);
        return new ResponseEntity<>(contactModel, HttpStatus.OK);

    }

    @DeleteMapping(value = "/deletecontact")
    public ResponseEntity deleteContact(@RequestParam(value = "id") Integer id) {
        Contact contact1 = contactService.findByPK(id);
        if (contact1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contact1.setDeleteFlag(Boolean.TRUE);
        contactService.update(contact1);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/deletecontacts")
    public ResponseEntity deleteContacts(@RequestBody DeleteModel ids) {
        try {
            contactService.deleleByIds(ids.getIds());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
