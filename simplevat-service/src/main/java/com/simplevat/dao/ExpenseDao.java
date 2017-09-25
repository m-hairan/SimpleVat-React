package com.simplevat.dao;

import java.util.Date;
import java.util.List;

import com.simplevat.entity.Expense;

public interface ExpenseDao extends Dao<Integer, Expense>{
	
	public List<Expense> getAllExpenses();
	
	public List<Object[]> getExpensePerMonth(Date startDate, Date endDate);
	
	/**
	 * This method will regurn list of all the expesnes for given start and end date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getExpenses(Date startDate, Date endDate);
	
	public List<Object[]> getVatOutPerMonthWise(Date startDate, Date endDate);
}
