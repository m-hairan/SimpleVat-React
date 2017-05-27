package com.simplevat.controller.expense;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Expense;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.expense.model.ExpenseModel;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.bankaccount.TransactionCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;

@Controller
@ManagedBean(name = "expenseController")
@RequestScoped
public class ExpenseController extends ExpenseControllerHelper implements Serializable  {

	private static final long serialVersionUID = 5366159429842989755L;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	@Autowired
	private TransactionCategoryService transactionCategoryService;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
    private ProjectService projectService;
	
	@Autowired
	private UserServiceNew<Integer, User> userServiceNew;
	
	@Value("${file.upload.location}")
	private String fileLocation;
	
	@Getter
	@Setter
	private ExpenseModel selectedExpenseModel;
	
	public String createExpense(){
		
		ExpenseModel expenseModel = new ExpenseModel();
		expenseModel.setExpenseId(0);
		this.setSelectedExpenseModel(expenseModel);
		
		return "/pages/secure/expense/create-expense.xhtml";
		
	}
	
	public String saveExpense(){
		
		Expense expense = this.getExpense(this.getSelectedExpenseModel());
			
		expense.setLastUpdateDate(LocalDateTime.now());
		expense.setLastUpdatedBy(12345);
		expense.setDeleteFlag(false);
		expense.setCreatedBy(12345);
		
		if(selectedExpenseModel.getTransactionType() != null){
			TransactionType transactionType = transactionTypeService.getTransactionType(selectedExpenseModel.getTransactionType().getTransactionTypeCode());
			expense.setTransactionType(transactionType);
		}
		if(selectedExpenseModel.getTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedExpenseModel.getTransactionCategory().getTransactionCategoryCode());
			expense.setTransactionCategory(transactionCategory);
		}
		if(selectedExpenseModel.getCurrency() != null){
			Currency currency = currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode());
			expense.setCurrency(currency);
		}
		if(selectedExpenseModel.getProject() != null){
			Project project = projectService.getProject(selectedExpenseModel.getProject().getProjectId());
			expense.setProject(project);
		}
		if(selectedExpenseModel.getUser() != null){
			User user = userServiceNew.getDao().findByPK(selectedExpenseModel.getUser().getUserId());
			expense.setUser(user);
		}
		
		if(this.getSelectedExpenseModel().getAttachmentFile().getSize() > 0){
			storeUploadedFile(this.getSelectedExpenseModel(), expense, fileLocation);
		}
		expenseService.updateOrCreateExpense(expense);
			
		return "/pages/secure/expense/expenses.xhtml";
		
	}
	
	public String saveAndContinueExpense(){
		
		Expense expense = this.getExpense(this.getSelectedExpenseModel());
		
		expense.setLastUpdateDate(LocalDateTime.now());
		expense.setLastUpdatedBy(12345);
		expense.setDeleteFlag(false);
		expense.setCreatedBy(12345);
		
		if(selectedExpenseModel.getTransactionType() != null){
			TransactionType transactionType = transactionTypeService.getTransactionType(selectedExpenseModel.getTransactionType().getTransactionTypeCode());
			expense.setTransactionType(transactionType);
		}
		if(selectedExpenseModel.getTransactionCategory() != null){
			TransactionCategory transactionCategory = transactionCategoryService.getTransactionCategory(selectedExpenseModel.getTransactionCategory().getTransactionCategoryCode());
			expense.setTransactionCategory(transactionCategory);
		}
		if(selectedExpenseModel.getCurrency() != null){
			Currency currency = currencyService.getCurrency(selectedExpenseModel.getCurrency().getCurrencyCode());
			expense.setCurrency(currency);
		}
		if(selectedExpenseModel.getProject() != null){
			Project project = projectService.getProject(selectedExpenseModel.getProject().getProjectId());
			expense.setProject(project);
		}
		if(selectedExpenseModel.getUser() != null){
			User user = userServiceNew.getDao().findByPK(selectedExpenseModel.getUser().getUserId());
			expense.setUser(user);
		}
		
		if(this.getSelectedExpenseModel().getAttachmentFile().getSize() > 0){
			storeUploadedFile(this.getSelectedExpenseModel(), expense, fileLocation);
		}
		
		expenseService.updateOrCreateExpense(expense);
		this.setSelectedExpenseModel(new ExpenseModel());
		return "/pages/secure/expense/create-expense.xhtml?faces-redirect=true";
		
	}
	
	public String deleteExpense(){
		
		Expense expense = this.getExpense(this.getSelectedExpenseModel());
		expense.setDeleteFlag(true);
		expenseService.updateOrCreateExpense(expense);
		return "/pages/secure/expense/expenses.xhtml?faces-redirect=true";
		
	}
	

}