package com.simplevat.dao.impl;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.CommonDao;
import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.ExpenseEntity;

@Repository
public class ExpenseDaoImpl extends CommonDao implements ExpenseDao  {

	public void saveExpense(ExpenseEntity expenseEntity) {
		this.getSessionFactory().getCurrentSession().save(expenseEntity);
	}

}
