package com.simplevat.dao.impl;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import java.util.List;

@Repository
@Transactional
public class ExpenseDaoImpl implements ExpenseDao  {

	@PersistenceContext
	private EntityManager entityManager;

	public Expense saveExpense(Expense expense) {
            entityManager.persist(expense);
            return expense;
	}

	@Override
	public List<Expense> getExpenses() {
		List<Expense> expenses = entityManager.createNamedQuery("allExpenses",
				Expense.class).getResultList();
		return expenses;
	}

	@Override
	public Expense updateExpense(Expense expense) {
		return entityManager.merge(expense);
	}

}
