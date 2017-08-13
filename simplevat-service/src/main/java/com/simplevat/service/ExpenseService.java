package com.simplevat.service;

import java.util.List;
import java.util.Map;

import com.simplevat.entity.Expense;

public interface ExpenseService extends SimpleVatService<Integer, Expense> {
	
	public List<Expense> getExpenses();
	
	public Expense updateOrCreateExpense(Expense expense);
	
	public Map<Object, Number> getExpensePerMonth();
	
	public Map<Object,Number> getVatOutPerMonth();
	
	public Number getVatOutQuartly();
	
	public int getMaxValue(Map<Object, Number> data);

}
