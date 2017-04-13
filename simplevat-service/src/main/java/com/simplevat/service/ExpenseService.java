package com.simplevat.service;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseService {
	
	public List<Expense> getExpenses();
	
	public Expense updateOrCreateExpense(Expense expense);

}
