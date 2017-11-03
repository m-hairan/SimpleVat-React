package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import com.simplevat.web.constant.TransactionStatusConstant;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Controller
@SpringScopeView
public class TransactionListController extends TransactionControllerHelper implements Serializable {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;
    @Autowired
    private TransactionStatusService transactionStatusService;
    @Autowired
    private TransactionTypeService transactionTypeService;
    @Autowired
    private BankAccountService bankAccountService;
    private List<TransactionModel> transactions;

    @Getter
    private BankAccountModel selectedBankAccountModel;

    @Getter
    @Setter
    private TransactionModel transactionModel;

    @PostConstruct
    public void init() {
        try {
            Integer bankAccountId = FacesUtil.getSelectedBankAccountId();
            BankAccount bankAccount = bankAccountService.findByPK(bankAccountId);
            selectedBankAccountModel = new BankAccountHelper().getBankAccountModel(bankAccount);
            BankAccountHelper bankAccountHelper = new BankAccountHelper();
            TransactionCriteria transactionCriteria = new TransactionCriteria();
            transactionCriteria.setActive(Boolean.TRUE);
            transactionCriteria.setBankAccount(bankAccountHelper.getBankAccount(selectedBankAccountModel));
            List<Transaction> transactionList = transactionService.getTransactionsByCriteria(transactionCriteria);

            transactions = new ArrayList<TransactionModel>();
            for (Transaction transaction : transactionList) {
                TransactionModel model = this.getTransactionModel(transaction);
                transactions.add(model);
            }
            Collections.sort(transactions);
        } catch (Exception ex) {
            Logger.getLogger(TransactionListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateTransactionDropDown() {
        Transaction transaction = getTransaction(transactionModel);
        if (transactionModel.getTransactionType() != null) {
            TransactionType transactionType = transactionTypeService.getTransactionType(transactionModel.getTransactionType().getTransactionTypeCode());
            transaction.setTransactionType(transactionType);
            transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
        }
        if (transactionModel.getExplainedTransactionCategory() != null) {
            TransactionCategory transactionCategory = transactionCategoryService.findByPK(transactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
            transaction.setExplainedTransactionCategory(transactionCategory);
        }
        if (transactionModel.getTransactionType() != null && transactionModel.getExplainedTransactionCategory() != null) {
            if (transactionModel.getTransactionStatus() == null
                    || transactionModel.getTransactionStatus().getExplainationStatusCode() == TransactionStatusConstant.UNEXPLIANED) {
                System.out.println("inside if :" + transaction.getTransactionStatus());
                TransactionStatus transactionStatus = transactionStatusService.findByPK(TransactionStatusConstant.EXPLIANED);
                transaction.setTransactionStatus(transactionStatus);
            }
        } else {

            System.out.println("inside inside if :" + transaction.getTransactionStatus());
            if (transactionModel.getTransactionStatus() == null
                    || transactionModel.getTransactionStatus().getExplainationStatusCode() == 0) {
                TransactionStatus transactionStatus = transactionStatusService.findByPK(TransactionStatusConstant.UNEXPLIANED);
                transaction.setTransactionStatus(transactionStatus);
            }
        }
        transactionService.update(transaction);
        init();
    }

    public List<TransactionModel> getTransactions() throws Exception {
        return transactions;
    }

    public void setTransactions(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionCategory> transactionCategories(final String searchQuery) throws Exception {
        TransactionCategoryCriteria transactionCategoryCriteria = new TransactionCategoryCriteria();
        transactionCategoryCriteria.setActive(Boolean.TRUE);
        return transactionCategoryService.getCategoriesByComplexCriteria(transactionCategoryCriteria);
    }

    public List<TransactionStatus> transactionStatuses(final String searchQuery) throws Exception {
        return transactionStatusService.executeNamedQuery("findAllTransactionStatues");
    }

    public List<TransactionType> transactionTypes() throws Exception {
        return transactionTypeService.findAll();

    }
}
