package com.simplevat.controller.bankaccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.bankaccount.model.TransactionModel;
import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.Project;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.ProjectService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionCategoryService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;
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
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
    private ProjectService projectService;
	
	@Autowired
	private TransactionStatusService<Integer, TransactionStatus> transactionStatusService;
	
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
		
		BankAccount bankAccount = bankAccountService.getBankAccount(selectedBankAccount.getBankAccountId());
		if(transaction.getDebitCreditFlag() == 'C'){
			bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().add(transaction.getTransactionAmount()));
		} else if (transaction.getDebitCreditFlag() == 'D'){
			bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().subtract(transaction.getTransactionAmount()));
		}
		
		if(selectedTransactionModel.getExplainedTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
			transaction.setExplainedTransactionCategory(transactionCategory);
		}
		
		if(selectedTransactionModel.getProject() != null){
			Project project = projectService.getProject(selectedTransactionModel.getProject().getProjectId());
			transaction.setProject(project);
		}
		
		if(selectedTransactionModel.getTransactionStatus() != null){
			TransactionStatus transactionStatus = transactionStatusService.findByPK(selectedTransactionModel.getTransactionStatus().getExplainationStatusCode());
			transaction.setTransactionStatus(transactionStatus);
		}
		
		if(selectedTransactionModel.getAttachmentFile().getSize() > 0){
			storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
		}
		
		if(selectedTransactionModel.getTransactionStatus() == null ||
		   selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0){
			Map<String,String> map = new HashMap<>();
			map.put("explainationStatusName", "EXPLAINED");
			TransactionStatus transactionStatus  = transactionStatusService.findByAttributes(map).get(0);
			transaction.setTransactionStatus(transactionStatus);
		}
		bankAccountService.createOrUpdateBankAccount(bankAccount);
		transaction.setCurrentBalance(bankAccount.getCurrentBalance());
		transactionService.updateOrCreateTransaction(transaction);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
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
		
		BankAccount bankAccount = bankAccountService.getBankAccount(selectedBankAccount.getBankAccountId());
		if(transaction.getDebitCreditFlag() == 'C'){
			bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().add(transaction.getTransactionAmount()));
		} else if (transaction.getDebitCreditFlag() == 'D'){
			bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().subtract(transaction.getTransactionAmount()));
		}
		
		if(selectedTransactionModel.getExplainedTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedTransactionModel.getExplainedTransactionCategory().getTransactionCategoryCode());
			transaction.setExplainedTransactionCategory(transactionCategory);
		}
		
		if(selectedTransactionModel.getTransactionStatus() != null){
			TransactionStatus transactionStatus = transactionStatusService.findByPK(selectedTransactionModel.getTransactionStatus().getExplainationStatusCode());
			transaction.setTransactionStatus(transactionStatus);
		}
		
		if(selectedTransactionModel.getTransactionStatus().getExplainationStatusCode() == 0){
			Map<String,String> map = new HashMap<>();
			map.put("explainationStatusName", "EXPLAINED");
			TransactionStatus transactionStatus  = transactionStatusService.findByAttributes(map).get(0);
			transaction.setTransactionStatus(transactionStatus);
		}
		
		if(selectedTransactionModel.getAttachmentFile().getSize() > 0){
			storeUploadedFile(selectedTransactionModel, transaction, fileLocation);
		}
		bankAccountService.createOrUpdateBankAccount(bankAccount);
		transactionService.updateOrCreateTransaction(transaction);
		this.setSelectedTransactionModel(new TransactionModel());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction saved successfully"));
		return "/pages/secure/bankaccount/edit-bank-transaction.xhtml?faces-redirect=true";
			
	}
	
	public void deleteTransaction(){
		
		Transaction transaction = getTransaction(selectedTransactionModel);
		transaction.setDeleteFlag(true);
		transactionService.updateOrCreateTransaction(transaction);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction deleted successfully"));
		
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
	
	public List<TransactionStatus> transactionStatuses(final String searchQuery) throws Exception {
		return transactionStatusService.executeNamedQuery("findAllTransactionStatues");
	}
}
