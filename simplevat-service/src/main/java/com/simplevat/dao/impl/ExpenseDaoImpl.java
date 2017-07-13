package com.simplevat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;

@Repository
@Transactional
public class ExpenseDaoImpl extends AbstractDao<Integer, Expense> implements ExpenseDao {

	@Override
	public List<Expense> getAllExpenses() {
		List<Expense> expenses = this.executeNamedQuery("allExpenses");
		return expenses;
	}


}
