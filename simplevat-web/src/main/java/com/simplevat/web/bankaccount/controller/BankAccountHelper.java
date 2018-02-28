/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.bankaccount.controller;

import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component
public class BankAccountHelper implements Serializable{

    public BankAccount getBankAccount(BankAccountModel model) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(model.getBankAccountId());
        bankAccount.setAccountNumber(model.getAccountNumber());
        bankAccount.setBankAccountCurrency(model.getBankAccountCurrency());
        bankAccount.setBankAccountName(model.getBankAccountName());
        bankAccount.setBankAccountStatus(model.getBankAccountStatus());
        bankAccount.setBankCountry(model.getBankCountry());
        bankAccount.setBankFeedStatusCode(model.getBankFeedStatusCode());
        bankAccount.setBankName(model.getBankName());
        bankAccount.setCreatedBy(model.getCreatedBy());
        bankAccount.setCreatedDate(model.getCreatedDate());
        bankAccount.setCurrentBalance(model.getCurrentBalance());
        bankAccount.setDeleteFlag(model.getDeleteFlag());
        bankAccount.setIbanNumber(model.getIbanNumber());
        bankAccount.setIsprimaryAccountFlag(model.getIsprimaryAccountFlag());
        bankAccount.setLastUpdateDate(model.getLastUpdateDate());
        bankAccount.setLastUpdatedBy(model.getLastUpdatedBy());
        bankAccount.setOpeningBalance(model.getOpeningBalance());
        bankAccount.setPersonalCorporateAccountInd(model.getPersonalCorporateAccountInd());
        bankAccount.setSwiftCode(model.getSwiftCode());
        bankAccount.setVersionNumber(model.getVersionNumber());
        return bankAccount;
    }
    
    public BankAccountModel getBankAccountModel(BankAccount bankAccount) {
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setBankAccountId(bankAccount.getBankAccountId());
        bankAccountModel.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountModel.setBankAccountCurrency(bankAccount.getBankAccountCurrency());
        bankAccountModel.setBankAccountName(bankAccount.getBankAccountName());
        bankAccountModel.setBankAccountStatus(bankAccount.getBankAccountStatus());
        bankAccountModel.setBankCountry(bankAccount.getBankCountry());
        bankAccountModel.setBankFeedStatusCode(bankAccount.getBankFeedStatusCode());
        bankAccountModel.setBankName(bankAccount.getBankName());
        bankAccountModel.setCreatedBy(bankAccount.getCreatedBy());
        bankAccountModel.setCreatedDate(bankAccount.getCreatedDate());
        bankAccountModel.setCurrentBalance(bankAccount.getCurrentBalance());
        bankAccountModel.setDeleteFlag(bankAccount.getDeleteFlag());
        bankAccountModel.setIbanNumber(bankAccount.getIbanNumber());
        bankAccountModel.setIsprimaryAccountFlag(bankAccount.getIsprimaryAccountFlag());
        bankAccountModel.setLastUpdateDate(bankAccount.getLastUpdateDate());
        bankAccountModel.setLastUpdatedBy(bankAccount.getLastUpdatedBy());
        bankAccountModel.setOpeningBalance(bankAccount.getOpeningBalance());
        bankAccountModel.setPersonalCorporateAccountInd(bankAccount.getPersonalCorporateAccountInd());
        bankAccountModel.setSwiftCode(bankAccount.getSwiftCode());
        bankAccountModel.setVersionNumber(bankAccount.getVersionNumber());
        return bankAccountModel;
    }
}
