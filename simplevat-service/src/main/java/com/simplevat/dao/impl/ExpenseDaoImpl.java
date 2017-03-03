package com.simplevat.dao.impl;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class ExpenseDaoImpl implements ExpenseDao  {

	@PersistenceContext
	private EntityManager entityManager;

	public Expense saveExpense(Expense expense) {

        entityManager.persist(expense);
		return expense;

	}

}
