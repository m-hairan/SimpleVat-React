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
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import java.io.Serializable;
import javax.annotation.PostConstruct;

@Controller
@SpringScopeView
public class ExpenseListController extends BaseController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Autowired
    private ExpenseService expenseService;
    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;
    @Getter
    @Setter
    private List<ExpenseModel> expenses;

    @Getter
    @Setter
    ExpenseControllerHelper controllerHelper;

    public ExpenseListController() {
        super(ModuleName.EXPENSE_MODULE);
    }

    @PostConstruct
    public void init() {
        controllerHelper = new ExpenseControllerHelper();
        List<Expense> expenseList = expenseService.getExpenses();
        expenses = new ArrayList<>();

        for (Expense expense : expenseList) {
            ExpenseModel model = controllerHelper.getExpenseModel(expense);
            expenses.add(model);
        }
    }

    public String redirectToEdit() {
        return "create-expense?faces-redirect=true&selectedExpenseModelId=" + selectedExpenseModel.getExpenseId();
    }

}
