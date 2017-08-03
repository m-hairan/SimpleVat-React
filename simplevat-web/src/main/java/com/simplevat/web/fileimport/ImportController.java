/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.fileimport;

import com.simplevat.web.bean.Transaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.primefaces.event.FileUploadEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@Scope("view")
public class ImportController {

    @Getter
    private List<Transaction> transactionList = new ArrayList<>();
    @Getter
    @Setter
    private List<Transaction> selectedTransaction;
    private List<Transaction> debitTransaction = new ArrayList<>();
    private List<Transaction> creditTransaction = new ArrayList<>();

    @PostConstruct
    public void init() {
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
                    if(transaction.getDrAmount() != null && !transaction.getDrAmount().trim().isEmpty() ){
                        debitTransaction.add(transaction);
                    }
                    if(transaction.getCrAmount() != null && !transaction.getCrAmount().trim().isEmpty() ){
                        creditTransaction.add(transaction);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectAllTranscation(){
        selectedTransaction.clear();
        selectedTransaction.addAll(transactionList);
    }
    
    public void selectDebitTranscation(){
        selectedTransaction.clear();
        selectedTransaction.addAll(debitTransaction);
    }
    
    public void selectCreditTranscation(){
        selectedTransaction.clear();
        selectedTransaction.addAll(creditTransaction);
    }
    
    public void clearSelectedTransaction(){
        selectedTransaction.clear();
    }
}
