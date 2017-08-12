package com.simplevat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.Dao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import com.simplevat.service.ExpenseService;
import com.simplevat.util.ChartUtil;

@Service("expenseService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ExpenseServiceImpl implements ExpenseService  {

	@Autowired
    public ExpenseDao expenseDao;
	
    @Autowired
    ChartUtil util;

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
		return expenseDao;
	}

	@Override
	public Map<Object, Number> getExpensePerMonth() {
		List<Object[]> rows = expenseDao.getExpensePerMonth(util.getStartDate().getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}

	@Override
	public int getMaxValue(Map<Object, Number> data) {
		return util.getMaxValue(data);
	}
	

}
