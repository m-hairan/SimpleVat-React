package org.simplevat.dao.expense;

import java.util.List;

import javax.annotation.Nonnull;

import org.simplevat.entity.expense.Expense;

public interface ExpenseDao {

    @Nonnull
    public List<Expense> getExpenseList();

}
