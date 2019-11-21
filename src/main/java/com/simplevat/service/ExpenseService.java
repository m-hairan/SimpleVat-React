package com.simplevat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.simplevat.entity.Expense;
import com.simplevat.service.report.model.BankAccountTransactionReportModel;

public abstract class ExpenseService extends SimpleVatService<Integer, Expense> {

    public abstract List<Expense> getExpenses();

    public abstract Expense updateOrCreateExpense(Expense expense);

    public abstract Map<Object, Number> getExpensePerMonth();

    public abstract Map<Object, Number> getExpensePerMonth(Date startDate, Date endDate);

    public abstract List<BankAccountTransactionReportModel> getExpensesForReport(Date startDate, Date endDate);

    public abstract Map<Object, Number> getVatOutPerMonth();

    public abstract int getVatOutQuartly();

    public abstract int getMaxValue(Map<Object, Number> data);

    public abstract List<Expense> getExpenseForReports(Date startDate, Date endDate);
}
