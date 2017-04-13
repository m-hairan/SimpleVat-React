package com.simplevat.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;

@Repository
@Transactional
public class ExpenseDaoImpl implements ExpenseDao  {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Expense> getExpenses() {
		List<Expense> expenses = entityManager.createNamedQuery("allExpenses",
				Expense.class).getResultList();
		return expenses;
	}

	@Override
	public Expense updateOrCreateExpense(Expense expense) {
		return entityManager.merge(expense);
	}

}
