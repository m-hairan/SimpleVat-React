package com.simplevat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.Dao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import com.simplevat.service.ExpenseService;

@Service("expenseService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ExpenseServiceImpl implements ExpenseService  {

	@Autowired
    public ExpenseDao expenseDao;
	

	@Override
	public List<Expense> getExpenses() {
		return expenseDao.getAllExpenses();
	}

	@Override
	public Expense updateOrCreateExpense(Expense expense) {
		return this.update(expense, expense.getExpenseId());
	}

	@Override
	public Dao<Integer, Expense> getDao() {
		// TODO Auto-generated method stub
		return expenseDao;
	}
	

}
