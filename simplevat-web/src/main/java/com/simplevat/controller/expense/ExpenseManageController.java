package com.simplevat.controller.expense;

import com.simplevat.entity.Expense;
import com.simplevat.expense.model.ExpenseModel;
import com.simplevat.service.ExpenseService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;

@ViewScoped
@ManagedBean(name = "expenseManageController")
public class ExpenseManageController extends ExpenseControllerHelper implements Serializable {

    private static final long serialVersionUID = 5366159429842989755L;

    @ManagedProperty(value = "#{expenseService}")
    @Getter
    @Setter
    private ExpenseService expenseService;

    @ManagedProperty(value = "#{expenseScreenController}")
    @Getter
    @Setter
    private ExpenseScreenController expenseScreenController;

    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;

    public void createExpense() {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.setExpenseId(0);
        this.setSelectedExpenseModel(expenseModel);
        expenseScreenController.setShowNewExpensePanel(true);
        expenseScreenController.setShowAllExpensePanel(false);

    }

    public void saveExpense() {

        Expense expense = this.getExpense(this.getSelectedExpenseModel());

        if (expense.getExpenseId() > 0) {
            // update existing expense
        } else {
            // save new expense
            Date now = new Date();
            expense.setCreatedDate(now);
            expense.setLastUpdateDate(now);
            expense.setLastUpdatedBy(12345);
            expense.setDeleteFlag('N');
            expense.setCreatedBy(null);
            expense.setClaimantId(null);
            expense.setTransactionCategoryCode(null);
            expense.setTransactionTypeCode(null);
            expense.setCurrencyCode(null);
            expense.setProjectId(null);
        }

        expenseService.saveExpense(expense);
        //storeUploadedFile(this.getSelectedExpenseModel());

    }

    public void cancelExpense() {
        expenseScreenController.setShowAllExpensePanel(true);
        expenseScreenController.setShowNewExpensePanel(false);
    }

}