/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.fileimport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.bean.Transaction;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionEntryTypeConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class TransactionImportController implements Serializable {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionTypeService transactionTypeService;
    @Autowired
    private TransactionStatusService transactionStatusService;
    @Getter
    private List<Transaction> transactionList = new ArrayList<>();
    @Getter
    @Setter
    private List<Transaction> selectedTransaction;
    private List<Transaction> debitTransaction = new ArrayList<>();
    private List<Transaction> creditTransaction = new ArrayList<>();
    @Getter
    private BankAccount bankAccount;
    @Getter
    @Setter
    private StreamedContent file;
    @Getter
    @Setter
    private InputStream stream;

    @PostConstruct
    public void init() {
        Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
        if (bankAccountId != null) {
            bankAccount = bankAccountService.findByPK(bankAccountId);
        }
        stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/excel-file/SampleTransaction.csv");
        file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "SampleTransaction.csv");
    }

    public void handleFileUpload(FileUploadEvent event) {
        transactionList.clear();
        debitTransaction.clear();
        creditTransaction.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(event.getFile().getInputstream()));
            CSVParser parser = new CSVParser(br, CSVFormat.EXCEL);
            List<CSVRecord> list = parser.getRecords();
            int recordNo = 0;
            int headerCount = 1;
            int headerIndex = 0;
            for (CSVRecord cSVRecord : list) {
                if (headerIndex <= headerCount) {
                    headerIndex++;
                } else {
                    Transaction transaction = new Transaction();
                    transaction.setId(++recordNo);
                    int i = 0;
                    transaction.setTransactionDate(cSVRecord.get(i++));
                    transaction.setDescription(cSVRecord.get(i++));
                    transaction.setDrAmount(cSVRecord.get(i++));
                    transaction.setCrAmount(cSVRecord.get(i++));
                    transactionList.add(transaction);
                    if (transaction.getDrAmount() != null && !transaction.getDrAmount().trim().isEmpty()) {
                        debitTransaction.add(transaction);
                    }
                    if (transaction.getCrAmount() != null && !transaction.getCrAmount().trim().isEmpty()) {
                        creditTransaction.add(transaction);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TransactionImportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String saveTransactions() {
        for (Transaction transaction : transactionList) {
            save(transaction);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
        return "bank-transactions?faces-redirect=true";
    }

    public void downloadXLFile() {

    }

    private void save(Transaction transaction) {
        try {
            User loggedInUser = FacesUtil.getLoggedInUser();
            com.simplevat.entity.bankaccount.Transaction transaction1 = new com.simplevat.entity.bankaccount.Transaction();
            transaction1.setLastUpdateBy(loggedInUser.getUserId());
            transaction1.setCreatedBy(loggedInUser.getUserId());
            transaction1.setBankAccount(bankAccount);
            transaction1.setEntryType(TransactionEntryTypeConstant.IMPORT);
            transaction1.setTransactionDescription(transaction.getDescription());
            LocalDate date = LocalDate.parse(transaction.getTransactionDate(), DateTimeFormatter.ofPattern("M/d/yyyy"));
            LocalTime time = LocalTime.now();
            transaction1.setTransactionDate(LocalDateTime.of(date, time));
            if (transaction.getDrAmount() != null && !transaction.getDrAmount().trim().isEmpty()) {
                transaction1.setTransactionAmount(BigDecimal.valueOf(Double.parseDouble(transaction.getDrAmount().replaceAll(",", ""))));
                transaction1.setDebitCreditFlag(TransactionCreditDebitConstant.DEBIT);
            }
            if (transaction.getCrAmount() != null && !transaction.getCrAmount().trim().isEmpty()) {
                transaction1.setTransactionAmount(BigDecimal.valueOf(Double.parseDouble(transaction.getCrAmount().replaceAll(",", ""))));
                transaction1.setDebitCreditFlag(TransactionCreditDebitConstant.CREDIT);
            }
            transaction1.setTransactionStatus(transactionStatusService.findByPK(TransactionStatusConstant.UNEXPLAINED));
            transactionService.persist(transaction1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAllTranscation() {
        selectedTransaction.clear();
        selectedTransaction.addAll(transactionList);
    }

    public void selectDebitTranscation() {
        selectedTransaction.clear();
        selectedTransaction.addAll(debitTransaction);
    }

    public void selectCreditTranscation() {
        selectedTransaction.clear();
        selectedTransaction.addAll(creditTransaction);
    }

    public void clearSelectedTransaction() {
        selectedTransaction.clear();
    }
}
