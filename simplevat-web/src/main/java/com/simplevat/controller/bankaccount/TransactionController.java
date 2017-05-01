package com.simplevat.controller.bankaccount;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionCategoryService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionTypeService;

@Controller
@ManagedBean(name = "transactionController")
@RequestScoped
public class TransactionController extends TransactionControllerHelper{
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	@Autowired
	private TransactionCategoryService transactionCategoryService;
	
	@Value("${file.upload.location}")
	private String fileLocation;
	
	@Getter
	@Setter
	private TransactionModel selectedTransactionModel;
	
	@Getter
	@Setter
	private BankAccount selectedBankAccount;
	
	public String createTransaction(){
		 this.selectedTransactionModel = new TransactionModel();
		 
		 return "/pages/secure/bankaccount/edit-bank-transaction.xhtml";
	}

	public String saveTransaction(){

		Transaction transaction = getTransaction(selectedTransactionModel);
		
		transaction.setLastUpdatedBy(12345);
		transaction.setCreatedBy(12345);
		transaction.setBankAccount(selectedBankAccount);
		
		if(selectedTransactionModel.getTransactionType() != null){
			TransactionType transactionType = transactionTypeService.getTransactionType(selectedTransactionModel.getTransactionType().getTransactionTypeCode());
			transaction.setTransactionType(transactionType);
			transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
		}
		if(selectedTransactionModel.getExplainedTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
			transaction.setExplainedTransactionCategory(transactionCategory);
		}
		
		if(selectedTransactionModel.getAttachmentFile().getSize() > 0){
			storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
		}
		
		transactionService.updateOrCreateTransaction(transaction);

		return "/pages/secure/bankaccount/bank-transactions.xhtml";
			
	}
	
	public String saveAndContinueTransaction(){

		Transaction transaction = getTransaction(selectedTransactionModel);
		
		transaction.setLastUpdatedBy(12345);
		transaction.setCreatedBy(12345);
		transaction.setBankAccount(selectedBankAccount);
		

		if(selectedTransactionModel.getTransactionType() != null){
			TransactionType transactionType = transactionTypeService.getTransactionType(selectedTransactionModel.getTransactionType().getTransactionTypeCode());
			transaction.setTransactionType(transactionType);
			transaction.setDebitCreditFlag(transactionType.getDebitCreditFlag());
		}
		if(selectedTransactionModel.getExplainedTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
			transaction.setExplainedTransactionCategory(transactionCategory);
		}
		
		if(selectedTransactionModel.getAttachmentFile().getSize() > 0){
			storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
		}
		
		transactionService.updateOrCreateTransaction(transaction);
		this.setSelectedTransactionModel(new TransactionModel());
		return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";
			
	}
	
	public String deleteTransaction(){
		
		Transaction transaction = getTransaction(selectedTransactionModel);
		transaction.setDeleteFlag(true);
		transactionService.updateOrCreateTransaction(transaction);
		return "/pages/secure/bankaccount/bank-transactions.xhtml?faces-redirect=true";
		
	}
	
	public List<TransactionType> transactionTypes(final String searchQuery) throws Exception {
		TransactionTypeCriteria transactionTypeCriteria = new TransactionTypeCriteria();
		transactionTypeCriteria.setActive(Boolean.TRUE);
		return transactionTypeService.getTransactionTypesByCriteria(transactionTypeCriteria);
	}
	
	public List<TransactionCategory> transactionCategories(final String searchQuery) throws Exception {
		TransactionCategoryCriteria transactionCategoryCriteria = new TransactionCategoryCriteria();
		transactionCategoryCriteria.setActive(Boolean.TRUE);
		return transactionCategoryService.getTransactionCategoriesByCriteria(transactionCategoryCriteria);
	}
}
