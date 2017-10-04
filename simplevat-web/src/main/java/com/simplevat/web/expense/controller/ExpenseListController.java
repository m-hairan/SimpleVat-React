package com.simplevat.web.expense.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Expense;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.ExpenseService;
import java.io.Serializable;

@Controller
@SpringScopeView
public class ExpenseListController extends ExpenseControllerHelper implements Serializable {

    @Autowired
    private ExpenseService expenseService;
    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;

    private List<ExpenseModel> expenses;

    public List<ExpenseModel> getExpenses() {

        List<Expense> expenseList = expenseService.getExpenses();

        expenses = new ArrayList<ExpenseModel>();

        for (Expense expense : expenseList) {
            ExpenseModel model = this.getExpenseModel(expense);
            expenses.add(model);
        }

        return expenses;
    }

    public void setExpenses(List<ExpenseModel> expenses) {
        this.expenses = expenses;
    }

    public String redirectToEdit() {
        return "create-expense?faces-redirect=true&selectedExpenseModelId=" + selectedExpenseModel.getExpenseId();
    }

}
