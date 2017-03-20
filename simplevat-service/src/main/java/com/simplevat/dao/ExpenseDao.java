package com.simplevat.dao;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseDao {
	
	public Expense saveExpense(Expense expense);
	
	public Expense updateExpense(Expense expense);
	
	public List<Expense> getExpenses();

}
