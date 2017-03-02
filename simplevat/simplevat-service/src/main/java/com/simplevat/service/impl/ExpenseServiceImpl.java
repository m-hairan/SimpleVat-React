package com.simplevat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.ExpenseEntity;
import com.simplevat.service.ExpenseService;

@Service("expenseService")
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService  {

	@Autowired
    public ExpenseDao expenseDao;
	
	public void saveExpense(ExpenseEntity expenseEntity) {
		expenseDao.saveExpense(expenseEntity);
	}

}
