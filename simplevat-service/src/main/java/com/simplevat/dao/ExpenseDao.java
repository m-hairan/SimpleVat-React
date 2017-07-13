package com.simplevat.dao;

import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseDao extends Dao<Integer, Expense>{
	
	public List<Expense> getAllExpenses();
	
}
