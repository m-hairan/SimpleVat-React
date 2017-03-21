package com.simplevat.controller.expense;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		if(expense.getExpenseId() > 0){
			
			Date now = new Date();
			expense.setLastUpdateDate(now);
			expense.setLastUpdatedBy(12345);
			expense.setDeleteFlag(false);
			expense.setCreatedBy(null);
			expense.setClaimantId(null);
			expense.setTransactionCategoryCode(null);
			expense.setTransactionTypeCode(null);
			expense.setCurrencyCode(null);
			expense.setProjectId(null);
			
			expenseService.updateExpense(expense);
			
		}else{
			// save expense 
			Date now = new Date();
			expense.setCreatedDate(now);
			expense.setLastUpdateDate(now);
			expense.setLastUpdatedBy(12345);
			expense.setDeleteFlag(false);
			expense.setCreatedBy(null);
			expense.setClaimantId(null);
			expense.setTransactionCategoryCode(null);
			expense.setTransactionTypeCode(null);
			expense.setCurrencyCode(null);
			expense.setProjectId(null);
			
			expenseService.saveExpense(expense);
		}
		
		//storeUploadedFile(this.getSelectedExpenseModel());
		
		return "/pages/secure/expense/expenses.xhtml";
		
	}
	
	public String saveAndContinueExpense(){
		
		Expense expense = this.getExpense(this.getSelectedExpenseModel());
		
		if(expense.getExpenseId() > 0){
			
			Date now = new Date();
			expense.setLastUpdateDate(now);
			expense.setLastUpdatedBy(12345);
			expense.setDeleteFlag(false);
			expense.setCreatedBy(null);
			expense.setClaimantId(null);
			expense.setTransactionCategoryCode(null);
			expense.setTransactionTypeCode(null);
			expense.setCurrencyCode(null);
			expense.setProjectId(null);
			
			expenseService.updateExpense(expense);
			
		}else{
			// save expense 
			Date now = new Date();
			expense.setCreatedDate(now);
			expense.setLastUpdateDate(now);
			expense.setLastUpdatedBy(12345);
			expense.setDeleteFlag(false);
			expense.setCreatedBy(null);
			expense.setClaimantId(null);
			expense.setTransactionCategoryCode(null);
			expense.setTransactionTypeCode(null);
			expense.setCurrencyCode(null);
			expense.setProjectId(null);
			
			expenseService.saveExpense(expense);
		}
		
		storeUploadedFile(this.getSelectedExpenseModel());
		
		return "/pages/secure/expense/create-expense.xhtml?faces-redirect=true";
		
	}
	
	public String deleteExpense(){
		
		Expense expense = this.getExpense(this.getSelectedExpenseModel());
		expense.setDeleteFlag(true);
		expenseService.updateExpense(expense);
		return "/pages/secure/expense/expenses.xhtml?faces-redirect=true";
		
	}

}