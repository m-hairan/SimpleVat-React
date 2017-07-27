package com.simplevat.web.bankaccount.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;
import com.simplevat.service.bankaccount.ImportedDraftTransactonService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class ImportedDraftTransactionListController extends TransactionControllerHelper{
	
	@Autowired
	private TransactionController transactionController;
	
	@Autowired
	private ImportedDraftTransactonService importedDraftTransactonService;
	
	private List<ImportedDraftTransaction> transactions;

	public List<ImportedDraftTransaction> getTransactions() throws Exception {
		ImportedDraftTransactionCriteria importedDraftTransactonCriteria = new ImportedDraftTransactionCriteria();
		importedDraftTransactonCriteria.setActive(Boolean.TRUE);
		importedDraftTransactonCriteria.setBankAccount(transactionController.getSelectedBankAccount());
		transactions = importedDraftTransactonService.getImportedDraftTransactionsByCriteria(importedDraftTransactonCriteria);
		return transactions;
	}

	public void setTransactions(List<ImportedDraftTransaction> transactions) {
		this.transactions = transactions;
	}
	

}
