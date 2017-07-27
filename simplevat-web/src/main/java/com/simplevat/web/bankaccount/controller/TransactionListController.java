package com.simplevat.web.bankaccount.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.bankaccount.TransactionService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class TransactionListController extends TransactionControllerHelper{
	
	@Autowired
	private TransactionController transactionController;
	
	@Autowired
	private TransactionService transactionService;
	
	private List<TransactionModel> transactions;

	public List<TransactionModel> getTransactions() throws Exception {
		TransactionCriteria transactionCriteria = new TransactionCriteria();
		transactionCriteria.setActive(Boolean.TRUE);
		transactionCriteria.setBankAccount(transactionController.getSelectedBankAccount());
		List<Transaction> transactionList = transactionService.getTransactionsByCriteria(transactionCriteria);
		
		transactions = new ArrayList<TransactionModel>();
		
		for(Transaction transaction : transactionList){
			TransactionModel model  = this.getTransactionModel(transaction);
			transactions.add(model);
		}
		
		return transactions;
	}

	public void setTransactions(List<TransactionModel> transactions) {
		this.transactions = transactions;
	}
	

}
