package com.simplevat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import com.simplevat.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService  {

	@Autowired
    public ExpenseDao expenseDao;
	
	public Expense saveExpense(Expense expense) {
		return expenseDao.saveExpense(expense);
	}

}
