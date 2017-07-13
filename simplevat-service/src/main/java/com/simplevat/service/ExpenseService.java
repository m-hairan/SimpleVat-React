package com.simplevat.service;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseService extends SimpleVatService<Integer, Expense> {
	
	public List<Expense> getExpenses();
	
	public Expense updateOrCreateExpense(Expense expense);

}
