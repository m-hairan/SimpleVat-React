package com.simplevat.web.expense.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Expense;
import com.simplevat.service.CompanyService;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Controller
@SpringScopeView
public class ExpenseListController extends BaseController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserServiceNew userServiceNew;
    @Autowired
    private CompanyService companyService;
    @Getter
    @Setter
    private ExpenseModel selectedExpenseModel;
    @Getter
    @Setter
    private List<ExpenseModel> expenses;

    @Getter
    @Setter
    ExpenseControllerHelper controllerHelper;

    @Getter
    @Setter
    Company company;

    public ExpenseListController() {
        super(ModuleName.EXPENSE_MODULE);
    }

    @PostConstruct
    public void init() {
        controllerHelper = new ExpenseControllerHelper();
        List<Expense> expenseList = expenseService.getExpenses();
        expenses = new ArrayList<>();
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());

        for (Expense expense : expenseList) {
            ExpenseModel model = controllerHelper.getExpenseModel(expense);
            model.setExpenseAmountCompanyCurrency(expense.getExpencyAmountCompanyCurrency());
            expenses.add(model);
        }
    }

    public String redirectToEdit() {
        return "expense?faces-redirect=true&selectedExpenseModelId=" + selectedExpenseModel.getExpenseId();
    }
    
    public String redirectToView() {
        return "expense-view?faces-redirect=true&selectedExpenseModelId=" + selectedExpenseModel.getExpenseId();
    }

    public String deleteExpense() {
        System.out.println("selected Model : :" + selectedExpenseModel.getExpenseId());
        Expense expense = controllerHelper.getExpense(selectedExpenseModel);
        expense.setDeleteFlag(true);
        expenseService.update(expense);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Expense deleted successfully"));
        return "list.xhtml?faces-redirect=true";
    }

}
