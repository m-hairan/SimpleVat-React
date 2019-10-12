/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.reports;

import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daynil
 */
@RestController
@RequestMapping("/rest/transactionreport")
public class TransactionReportRestController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    TransactionRestControllerHelper transactionRestControllerHelper;

    @RequestMapping(method = RequestMethod.GET, value = "/completefinancialperiods")
    public ResponseEntity<List<FinancialPeriodRest>> completeFinancialPeriods() {
        try {
            return new ResponseEntity(FinancialPeriodHolderRest.getFinancialPeriodList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactiontypes")
    public ResponseEntity<List<TransactionType>> transactionTypes() throws Exception {
        try {
            List<TransactionType> transactionTypeList = transactionTypeService.findAllChild();
            return new ResponseEntity(transactionTypeList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactioncategories")
    public ResponseEntity<List<TransactionCategory>> transactionCategories(@RequestParam("transactionTypeCode") Integer transactionTypeCode) throws Exception {
        try {
            TransactionType transactionType = transactionTypeService.findByPK(transactionTypeCode);
            System.out.println("transactionType==" + transactionType);
            String name = "";
            List<TransactionCategory> transactionCategoryParentList = new ArrayList<>();
            List<TransactionCategory> transactionCategoryList = new ArrayList<>();
            transactionCategoryList.clear();
            if (transactionType != null) {
                transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(transactionType.getTransactionTypeCode(), name);
                for (TransactionCategory transactionCategory : transactionCategoryList) {
                    if (transactionCategory.getParentTransactionCategory() != null) {
                        transactionCategoryParentList.add(transactionCategory.getParentTransactionCategory());
                    }
                }
                transactionCategoryList.removeAll(transactionCategoryParentList);
                return new ResponseEntity(transactionCategoryList, HttpStatus.OK);
            }
            return new ResponseEntity(transactionCategoryList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/view")
    public ResponseEntity<List<TransactionRestModel>> view(@RequestBody Integer transactionTypeCode, @RequestBody Integer transactionCategoryId, @RequestBody FinancialPeriodRest financialPeriod) {
        try {

            TransactionType transactionType = transactionTypeService.findByPK(transactionTypeCode);
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(transactionCategoryId);
            double totalTransactionAmount = 0.00;
            List<TransactionRestModel> transactionList = new ArrayList<>();
            List<Transaction> transactions = transactionService.getTransactionsByDateRangeAndTranscationTypeAndTranscationCategory(transactionType, transactionCategory, financialPeriod.getStartDate(), financialPeriod.getLastDate());
            if (transactions != null && !transactions.isEmpty()) {
                for (Transaction transaction : transactions) {
                    totalTransactionAmount = totalTransactionAmount + transaction.getTransactionAmount().doubleValue();
                    transactionList.add(transactionRestControllerHelper.getTransactionModel(transaction));
                }
            }
            return new ResponseEntity(transactionList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
