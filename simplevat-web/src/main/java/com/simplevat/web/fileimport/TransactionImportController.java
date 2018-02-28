/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.fileimport;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.BankAccount;
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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private List<Transaction> invalidTransactionList = new ArrayList<>();
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
    @Getter
    @Setter
    private Integer totalErrorRows = 0;
    @Getter
    @Setter
    private String dateFormat;
    @Getter
    @Setter
    private List<String> dateFormatList;
    List<CSVRecord> listParser = new ArrayList<>();
    @Getter
    @Setter
    private Integer headerCount;
    private List<String> invalidHeaderTransactionList = new ArrayList<>();
    @Getter
    @Setter
    private boolean headerIncluded = true;
    @Getter
    @Setter
    private String delimiter = ",";
    @Getter
    @Setter
    private boolean renderButtonOnValidData;

    @PostConstruct
    public void init() {
        Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
        if (bankAccountId != null) {
            bankAccount = bankAccountService.findByPK(bankAccountId);
        }
        try {
            stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/excel-file/SampleTransaction.csv");
            file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "SampleTransaction.csv");
        } catch (Exception e) {
        }
        dateFormat = TransactionStatusConstant.DEFAULT_DATE_FORMAT;
        dateFormatList = DateFormatUtil.dateFormatList();
        headerCount = TransactionStatusConstant.HEADER_COUNT;
    }

    public void handleFileUpload(FileUploadEvent event) {
        listParser.clear();
        try {
            InputStream inputStream = event.getFile().getInputstream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser parser = new CSVParser(br, CSVFormat.EXCEL);
            listParser = parser.getRecords();
        } catch (IOException e) {
        }
        populateTranscationOnFileUpload();
    }

    public void onChange() {
        populateTranscationOnFileUpload();
    }

    public void populateTranscationOnFileUpload() {
        transactionList.clear();
        debitTransaction.clear();
        creditTransaction.clear();
        invalidHeaderTransactionList.clear();
        totalErrorRows = 0;
        renderButtonOnValidData=true;
        try {
            if (listParser != null) {
                int recordNo = 0;
                int headerIndex = 0;
                Integer header;
                for (CSVRecord cSVRecord : listParser) {
                    if (headerIncluded) {
                        header = headerCount + 1;
                    } else {
                        header = headerCount;
                    }
                    if (headerIndex < header) {
                        headerIndex++;
                    } else {
                        Transaction transaction = new Transaction();
                        transaction.setId(++recordNo);
                        int i = 0;
                        String date = cSVRecord.get(i++);
                        String description = cSVRecord.get(i++);
                        String drAmount = cSVRecord.get(i++);
                        String crAmount = cSVRecord.get(i++);

                        try {

                            Date dateTranscation = new SimpleDateFormat(dateFormat).parse(date);
                            LocalDateTime transactionDate = Instant.ofEpochMilli(dateTranscation.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                            if (!drAmount.isEmpty()) {
                                new BigDecimal(Float.valueOf(drAmount));
                            }
                            if (!crAmount.isEmpty()) {
                                new BigDecimal(Float.valueOf(crAmount));
                            }
                            transaction.setTransactionDate(date);
                            transaction.setDescription(description);
                            transaction.setDrAmount(drAmount);
                            transaction.setCrAmount(crAmount);
                            transaction.setValidData(Boolean.TRUE);
                            transaction.setFormat(TransactionStatusConstant.VALID);
                            transactionList.add(transaction);
                        } catch (Exception e) {
                            totalErrorRows = totalErrorRows + 1;
                            transaction.setTransactionDate(date);
                            transaction.setDescription(description);
                            transaction.setDrAmount(drAmount);
                            transaction.setCrAmount(crAmount);
                            transaction.setValidData(Boolean.FALSE);
                            transaction.setFormat(TransactionStatusConstant.INVALID);
                            transactionList.add(transaction);
                            renderButtonOnValidData=false;
                        }
                        if (transaction.getDrAmount() != null && !transaction.getDrAmount().trim().isEmpty()) {
                            debitTransaction.add(transaction);
                        }
                        if (transaction.getCrAmount() != null && !transaction.getCrAmount().trim().isEmpty()) {
                            creditTransaction.add(transaction);
                        }
                    }
                }
                if (!invalidHeaderTransactionList.isEmpty()) {
                    StringBuilder validationMessage = new StringBuilder("Heading mismatch  ");
                    for (String invalidHeading : invalidHeaderTransactionList) {
                        validationMessage.append(invalidHeading + "  ");
                    }
                    validationMessage.append(" heading should be (" + TransactionStatusConstant.TRANSACTION_DATE + "," + TransactionStatusConstant.DESCRIPTION + "," + TransactionStatusConstant.DEBIT_AMOUNT + "," + TransactionStatusConstant.CREDIT_AMOUNT + ")");
                    FacesMessage message = new FacesMessage(validationMessage.toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage("validationId", message);
                }
            }
        } catch (Exception ex) {
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

    public List<String> completeDateFormat() {
        return dateFormatList;
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
