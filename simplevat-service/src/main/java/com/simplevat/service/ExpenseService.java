package com.simplevat.service;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseService {
	
	public Expense saveExpense(Expense expense);
	
	public List<Expense> getExpenseList();

}
