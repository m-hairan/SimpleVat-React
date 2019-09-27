/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.bankaccountcontroller;

import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.BankAccountStatus;
import com.simplevat.entity.bankaccount.BankAccountType;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.BankAccountTypeService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.BankAccountStatusService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import java.io.Serializable;
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
@RequestMapping(value = "/bank")
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
    private ResponseEntity<List<BankAccount>> getBankAccountList() {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
        if (bankAccounts != null) {
            return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/savebank")
    private ResponseEntity saveBankAccount(@RequestBody BankAccount bankAccount, @RequestParam(value = "id") Integer id) {
        try {

            User user = userServiceNew.findByPK(id);
            bankAccount.setCreatedBy(user.getUserId());

            if (bankAccount.getBankAccountStatus() != null) {
                BankAccountStatus bankAccountStatus = bankAccountStatusService
                        .getBankAccountStatus(bankAccount.getBankAccountStatus().getBankAccountStatusCode());
                bankAccount.setBankAccountStatus(bankAccountStatus);
            }
            if (bankAccount.getBankAccountCurrency() != null) {
                Currency currency = currencyService
                        .getCurrency(bankAccount.getBankAccountCurrency().getCurrencyCode());
                bankAccount.setBankAccountCurrency(currency);
            }

            if (bankAccount.getBankAccountType() != null) {
                BankAccountType bankAccountType = bankAccountTypeService.getBankAccountType(bankAccount.getBankAccountType().getId());
                bankAccount.setBankAccountType(bankAccountType);
            }

            if (bankAccount.getBankAccountId() == null || bankAccount.getBankAccountId() == 0) {
                bankAccount.setCurrentBalance(bankAccount.getOpeningBalance());
                BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatusByName("ACTIVE");
                bankAccount.setBankAccountStatus(bankAccountStatus);
            }
            if (bankAccount.getBankAccountId() == null) {
                bankAccountService.persist(bankAccount);
            } else {
                bankAccountService.update(bankAccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getaccounttype")
    private ResponseEntity<List<BankAccountType>> getBankAccontType() {
        List<BankAccountType> bankAccountTypes = bankAccountTypeService.getBankAccountTypeList();
        if (bankAccountTypes != null) {
            return new ResponseEntity<>(bankAccountTypes, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getcountry")
    private ResponseEntity<List<Country>> completeCountry(@RequestParam(value = "countryStr") String countryStr) {
        List<Country> countrySuggestion = new ArrayList<>();
        List<Country> countries = countryService.getCountries();
        if (countries == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    private ResponseEntity deleteBankAccount(@RequestParam("id") Integer id) {
        BankAccount bankAccount = bankAccountService.findByPK(id);
        bankAccount.setDeleteFlag(true);
        bankAccountService.update(bankAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/editbank")
    private ResponseEntity<BankAccount> editBankAccount(@RequestParam("id") Integer id) {
        BankAccount bankAccount = bankAccountService.findByPK(id);
        if (bankAccount != null) {
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
