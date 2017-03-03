package com.simplevat.test.dao;

import com.simplevat.entity.Expense;
import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;

/**
 * Created by mohsin on 3/3/2017.
 */
public class TestExpenseDao extends BaseManagerTest{

    @Test
    public void testSaveExpense()
    {
        Expense expense = new Expense();
        expense.setExpenseDescription("Test Expense");
        Expense newExpense = this.expenseDao.saveExpense(expense);

        System.out.println(" New Expense id is :" + newExpense.getExpenseId());
    }
}
