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

import com.simplevat.entity.Expense;
import com.simplevat.expense.model.ExpenseModel;
import com.simplevat.service.ExpenseService;

@Controller
@ManagedBean(name = "expenseController")
@RequestScoped
public class ExpenseController extends ExpenseControllerHelper implements Serializable  {

	private static final long serialVersionUID = 5366159429842989755L;
	
	@Autowired
	private ExpenseService expenseService;
	
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
		expense.setClaimantId(null);
		expense.setTransactionCategoryCode(null);
		expense.setTransactionTypeCode(null);
		expense.setCurrencyCode(null);
		expense.setProjectId(null);
		
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
		expense.setClaimantId(null);
		expense.setTransactionCategoryCode(null);
		expense.setTransactionTypeCode(null);
		expense.setCurrencyCode(null);
		expense.setProjectId(null);
		
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