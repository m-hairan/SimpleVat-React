package org.simplevat.dao.expense.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.simplevat.dao.expense.ExpenseDao;
import org.simplevat.entity.expense.Expense;
import org.simplevat.entity.expense.ExpenseType;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    @Override
    @Nonnull
    public List<Expense> getExpenseList() {
        List<Expense> expenseList = new ArrayList<Expense>();

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.GAS);
            expense.setAmount(1000d);
            expense.setDescription("GAS Expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.ELECTRICITY);
            expense.setAmount(5000d);
            expense.setDescription("Electricity Expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.EMPLOYEE_SALARY);
            expense.setAmount(50000d);
            expense.setDescription("Employee Salary");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.INSURANCE);
            expense.setAmount(1000d);
            expense.setDescription("Insurance expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.MACHINERY);
            expense.setAmount(100000d);
            expense.setDescription("Machinery Expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.MORTGAGE);
            expense.setAmount(100000d);
            expense.setDescription("Mortgage Expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        for (int i = 0; i < 5; i++) {
            Expense expense = new Expense();
            expense.setType(ExpenseType.WATER);
            expense.setAmount(5000d);
            expense.setDescription("Water Expenses");
            expense.setYear(1999 + i);
            expenseList.add(expense);
        }

        return expenseList;
    }

}
