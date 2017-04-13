package com.simplevat.dao;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseDao {
	
	public List<Expense> getExpenses();
	
	public Expense updateOrCreateExpense(Expense expense);

}
