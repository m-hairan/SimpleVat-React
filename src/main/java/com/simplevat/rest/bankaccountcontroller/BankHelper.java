/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.bankaccountcontroller;

import com.simplevat.entity.Currency;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.BankAccountStatus;
import com.simplevat.entity.bankaccount.BankAccountType;
import com.simplevat.service.BankAccountTypeService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.BankAccountStatusService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Sonu
 */
public class BankHelper {

    public BankAccount getBankAccountByBankAccountModel(BankModel bankModel, BankAccountStatusService bankAccountStatusService, CurrencyService currencyService, BankAccountTypeService bankAccountTypeService, CountryService countryService) {
        BankAccount bankAccount = new BankAccount();
        if (bankModel.getBankCountry() != null) {
            bankAccount.setBankCountry(countryService.getCountry(bankModel.getBankCountry()));
        }
        bankAccount.setAccountNumber(bankModel.getAccountNumber());
        bankAccount.setBankAccountName(bankModel.getBankAccountName());
        bankAccount.setBankName(bankModel.getBankName());
        bankAccount.setDeleteFlag(Boolean.FALSE);
        bankAccount.setIbanNumber(bankModel.getIbanNumber());
        bankAccount.setIsprimaryAccountFlag(bankModel.getIsprimaryAccountFlag());
        bankAccount.setOpeningBalance(bankModel.getOpeningBalance());
        bankAccount.setPersonalCorporateAccountInd(bankModel.getPersonalCorporateAccountInd());
        bankAccount.setSwiftCode(bankModel.getSwiftCode());
        bankAccount.setVersionNumber(1);

        if (bankModel.getBankAccountStatus() != null) {
            BankAccountStatus bankAccountStatus = bankAccountStatusService
                    .getBankAccountStatus(bankModel.getBankAccountStatus());
            bankAccount.setBankAccountStatus(bankAccountStatus);
        }
        if (bankModel.getBankAccountCurrency() != null) {
            Currency currency = currencyService
                    .getCurrency(Integer.valueOf(bankModel.getBankAccountCurrency()));
            bankAccount.setBankAccountCurrency(currency);
        }

        if (bankModel.getBankAccountType() != null) {
            BankAccountType bankAccountType = bankAccountTypeService.getBankAccountType(bankModel.getBankAccountType());
            bankAccount.setBankAccountType(bankAccountType);
        }

        if (bankModel.getBankAccountId() == null || bankModel.getBankAccountId() == 0) {
            bankAccount.setCurrentBalance(bankModel.getOpeningBalance());
            BankAccountStatus bankAccountStatus = bankAccountStatusService.getBankAccountStatusByName("ACTIVE");
            bankAccount.setBankAccountStatus(bankAccountStatus);
        }

        return bankAccount;
    }

}
