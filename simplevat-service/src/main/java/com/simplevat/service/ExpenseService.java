package com.simplevat.service;

import java.util.List;

import com.simplevat.entity.Expense;

import java.util.List;

public interface ExpenseService {
	
	public Expense saveExpense(Expense expense);
	
	public Expense updateExpense(Expense expense);
	
	public List<Expense> getExpenses();

}
