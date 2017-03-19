package com.simplevat.service;

import com.simplevat.entity.Expense;

import java.util.List;

public interface ExpenseService {

    Expense saveExpense(Expense expense);

    List<Expense> getExpenseList();

}
