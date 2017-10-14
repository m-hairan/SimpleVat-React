package com.simplevat.web.bankaccount.controller;

import com.simplevat.entity.bankaccount.BankAccount;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.simplevat.web.constant.BankAccountConstant;
import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

public class TransactionControllerHelper {

    public Transaction getTransaction(TransactionModel model) {
        System.out.println("model :" + model);
        Transaction transaction = new Transaction();
        transaction.setTransactionId(model.getTransactionId());
        if (model.getTransactionDate() != null) {
            LocalDateTime transactionDate = Instant.ofEpochMilli(model.getTransactionDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            transaction.setTransactionDate(transactionDate);
        }
        transaction.setTransactionDescription(model.getTransactionDescription());
        transaction.setTransactionAmount(model.getTransactionAmount());
        transaction.setTransactionType(model.getTransactionType());
        transaction.setReceiptNumber(model.getReceiptNumber());
        transaction.setDebitCreditFlag(model.getDebitCreditFlag());
        transaction.setProject(model.getProject());
        transaction.setExplainedTransactionCategory(model.getExplainedTransactionCategory());
        transaction.setExplainedTransactionDescription(model.getExplainedTransactionDescription());
        transaction.setExplainedTransactionAttachementDescription(model.getExplainedTransactionAttachementDescription());
        transaction.setExplainedTransactionAttachement(model.getExplainedTransactionAttachement());
        transaction.setBankAccount(model.getBankAccount());
        transaction.setTransactionStatus(model.getTransactionStatus());
        transaction.setCurrentBalance(model.getCurrentBalance());
        transaction.setCreatedBy(model.getCreatedBy());
        transaction.setCreatedDate(model.getCreatedDate());
        transaction.setLastUpdatedBy(model.getLastUpdatedBy());
        transaction.setLastUpdateDate(model.getLastUpdateDate());
        transaction.setDeleteFlag(model.getDeleteFlag());
        transaction.setVersionNumber(model.getVersionNumber());
        return transaction;
    }

    public TransactionModel getTransactionModel(Transaction entity) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(entity.getTransactionId());

        if (entity.getTransactionDate() != null) {
            Date transactionDate = Date.from(entity.getTransactionDate().atZone(ZoneId.systemDefault()).toInstant());
            transactionModel.setTransactionDate(transactionDate);
        }

        transactionModel.setTransactionDescription(entity.getTransactionDescription());
        transactionModel.setTransactionAmount(entity.getTransactionAmount());
        transactionModel.setTransactionType(entity.getTransactionType());
        transactionModel.setReceiptNumber(entity.getReceiptNumber());
        transactionModel.setDebitCreditFlag(entity.getDebitCreditFlag());
        transactionModel.setProject(entity.getProject());
        transactionModel.setExplainedTransactionCategory(entity.getExplainedTransactionCategory());
        transactionModel.setExplainedTransactionDescription(entity.getExplainedTransactionDescription());
        transactionModel.setExplainedTransactionAttachementDescription(entity.getExplainedTransactionAttachementDescription());
        transactionModel.setExplainedTransactionAttachement(entity.getExplainedTransactionAttachement());
        transactionModel.setTransactionStatus(entity.getTransactionStatus());
        transactionModel.setBankAccount(entity.getBankAccount());
        transactionModel.setCurrentBalance(entity.getCurrentBalance());
        transactionModel.setCreatedBy(entity.getCreatedBy());
        transactionModel.setCreatedDate(entity.getCreatedDate());
        transactionModel.setLastUpdatedBy(entity.getLastUpdatedBy());
        transactionModel.setLastUpdateDate(entity.getLastUpdateDate());
        transactionModel.setDeleteFlag(entity.getDeleteFlag());
        transactionModel.setVersionNumber(entity.getVersionNumber());

        byte[] attachmentPath = entity.getExplainedTransactionAttachement();
        if (attachmentPath != null) {
            InputStream inputStream = new ByteArrayInputStream(attachmentPath);
                transactionModel.setAttachmentFileContent(new DefaultStreamedContent(inputStream));
        }

        return transactionModel;
    }

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

}
