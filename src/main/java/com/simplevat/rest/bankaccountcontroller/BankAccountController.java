/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.bankaccountcontroller;

import com.simplevat.helper.BankHelper;
import com.simplevat.contact.model.BankModel;
import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.BankAccountStatus;
import com.simplevat.entity.bankaccount.BankAccountType;
import com.simplevat.service.BankAccountTypeService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.BankAccountService;
import com.simplevat.service.BankAccountStatusService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import java.io.Serializable;
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
@RequestMapping(value = "/rest/bank")
public class BankAccountController implements Serializable {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountStatusService bankAccountStatusService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private BankAccountTypeService bankAccountTypeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private TransactionStatusService transactionStatusService;

    @GetMapping(value = "/getbanklist")
    public ResponseEntity<List<BankAccount>> getBankAccountList() {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
        if (bankAccounts != null) {
            return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/savebank")
    public ResponseEntity saveBankAccount(@RequestBody BankModel bankModel, @RequestParam(value = "id") Integer id) {
        try {
            BankAccount bankAccount = BankHelper.getBankAccountByBankAccountModel(bankModel, bankAccountStatusService, currencyService, bankAccountTypeService, countryService);
            User user = userServiceNew.findByPK(id);
            if (bankModel.getBankAccountId() == null) {
                if (user != null) {
                    bankAccount.setCreatedDate(LocalDateTime.now());
                    bankAccount.setCreatedBy(user.getUserId());
                }
                bankAccountService.persist(bankAccount);
            } else {
                bankAccount.setBankAccountId(bankModel.getBankAccountId());
                bankAccount.setLastUpdateDate(LocalDateTime.now());
                bankAccount.setLastUpdatedBy(user.getUserId());
                bankAccountService.update(bankAccount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/getaccounttype")
    public ResponseEntity<List<BankAccountType>> getBankAccontType() {
        List<BankAccountType> bankAccountTypes = bankAccountTypeService.getBankAccountTypeList();
        if (bankAccountTypes != null) {
            return new ResponseEntity<>(bankAccountTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getbankaccountstatus")
    public ResponseEntity<List<BankAccountStatus>> getBankAccountStatus() {
        List<BankAccountStatus> bankAccountStatuses = bankAccountStatusService.getBankAccountStatuses();
        return new ResponseEntity<>(bankAccountStatuses, HttpStatus.OK);
    }

    @GetMapping(value = "/getcountry")
    public ResponseEntity<List<Country>> completeCountry(@RequestParam(value = "countryStr") String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        List<Country> countries = countryService.getCountries();
        if (countries == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Iterator<Country> countryIterator = countries.iterator();
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

    @DeleteMapping(value = "/deletebank")
    public ResponseEntity deleteBankAccount(@RequestParam("id") Integer id) {
        BankAccount bankAccount = bankAccountService.findByPK(id);
        bankAccount.setDeleteFlag(true);
        bankAccountService.update(bankAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid")
    public ResponseEntity<BankAccount> editBankAccount(@RequestParam("id") Integer id) {
        try {
            BankAccount bankAccount = bankAccountService.findByPK(id);
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/getcurrenncy")
    public ResponseEntity<List<Currency>> getCurrency(@RequestParam(value = "currencyStr") String currencyStr) {
        List<Currency> currencySuggestion = new ArrayList<>();
        List<Currency> currencies = currencyService.getCurrencies();

        Iterator<Currency> currencyIterator = null;
        if (currencies != null && !currencies.isEmpty()) {
            currencyIterator = currencies.iterator();
        }
        if (currencyIterator == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
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
}
