/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.bankaccountcontroller;

import com.simplevat.bank.model.DeleteModel;
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
    public ResponseEntity getBankAccountList() {
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
    public ResponseEntity getBankAccontType() {
        List<BankAccountType> bankAccountTypes = bankAccountTypeService.getBankAccountTypeList();
        if (bankAccountTypes != null && !bankAccountTypes.isEmpty()) {
            return new ResponseEntity<>(bankAccountTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getbankaccountstatus")
    public ResponseEntity getBankAccountStatus() {
        List<BankAccountStatus> bankAccountStatuses = bankAccountStatusService.getBankAccountStatuses();
        if (bankAccountStatuses != null && !bankAccountStatuses.isEmpty()) {
            return new ResponseEntity<>(bankAccountStatuses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Deprecated
    @GetMapping(value = "/getcountry")
    public ResponseEntity getCountry() {
        try {
            List<Country> countries = countryService.getCountries();
            if (countries != null && !countries.isEmpty()) {
                return new ResponseEntity<>(countries, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/deletebank")
    public ResponseEntity deleteBankAccount(@RequestParam("id") Integer id) {
        BankAccount bankAccount = bankAccountService.findByPK(id);
        bankAccount.setDeleteFlag(true);
        bankAccountService.update(bankAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid")
    public ResponseEntity getById(@RequestParam("id") Integer id) {
        try {
            BankAccount bankAccount = bankAccountService.findByPK(id);
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/deletebanks")
    public ResponseEntity deleteBankAccounts(@RequestBody DeleteModel ids) {
        try {
            bankAccountService.deleteByIds(ids.getIds());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Deprecated
    @GetMapping(value = "/getcurrenncy")
    public ResponseEntity getCurrency() {
        try {
            List<Currency> currencies = currencyService.getCurrencies();
            if (currencies != null && !currencies.isEmpty()) {
                return new ResponseEntity<>(currencies, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
