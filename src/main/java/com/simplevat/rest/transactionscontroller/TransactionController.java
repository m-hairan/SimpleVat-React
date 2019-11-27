/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.transactionscontroller;

import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.bankaccount.TransactionService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonu
 */
@RestController
@RequestMapping(value = "/rest/transaction")
public class TransactionController implements Serializable {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/gettransactions")
    public ResponseEntity getAllTransaction() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        TransactionModel transactionModel=new TransactionModel();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                transactionModel.setTransactionAmount(transaction.getTransactionAmount());
                transactionModel.setTransactionDescription(transaction.getTransactionDescription());
                transactionModel.setTransactionDate(transaction.getTransactionDate());
                transactionModel.setTransactionType(transaction.getTransactionType());
                transactionModel.setBankAccount(transaction.getBankAccount());
                transactionModel.setExplainedTransactionCategory(transaction.getExplainedTransactionCategory());
            }

            return new ResponseEntity(transactionModel, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}


