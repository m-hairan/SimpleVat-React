/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.expenses;

import com.simplevat.entity.Currency;
import com.simplevat.entity.Expense;
import com.simplevat.entity.Project;
import com.simplevat.entity.User;
import com.simplevat.entity.bankaccount.TransactionCategory;
import static com.simplevat.rest.expenses.ExpenseRestHelper.TRANSACTION_TYPE_EXPENSE;
import com.simplevat.service.CompanyService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.bankaccount.TransactionTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daynil
 */
@RestController
@RequestMapping("/rest/expense")
public class ExpenseRestController {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    TransactionTypeService transactionTypeService;

    ExpenseRestHelper controllerHelper = new ExpenseRestHelper();

    @RequestMapping(method = RequestMethod.GET, value = "/retrieveExpenseList")
    public ResponseEntity<List<ExpenseRestModel>> expenseList() {
        try {
            List<ExpenseRestModel> expenses = new ArrayList<>();
            System.out.println("expenseService=" + expenseService);
            System.out.println("transactionCategoryService=" + transactionCategoryService);
            List<Expense> expenseList = expenseService.getExpenses();
            for (Expense expense : expenseList) {
                ExpenseRestModel model = controllerHelper.getExpenseModel(expense);
                model.setExpenseAmountCompanyCurrency(expense.getExpencyAmountCompanyCurrency());
                expenses.add(model);
            }
            return new ResponseEntity(expenses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity saveExpense(@RequestBody ExpenseRestModel expenseRestModel) {
        try {
            controllerHelper.saveExpense(expenseRestModel, currencyService, userServiceNew, companyService, projectService, expenseService, transactionCategoryService, transactionTypeService);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vieworedit")
    public ResponseEntity<ExpenseRestModel> viewExpense(@RequestParam("expenseId") Integer expenseId) {
        try {
            System.out.println("expenseId=" + expenseId);
            return new ResponseEntity(controllerHelper.viewOrEditExpense(expenseId, expenseService), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity deleteExpense(@RequestParam("expenseId") Integer expenseId) {
        try {
            System.out.println("expenseId=" + expenseId);
            controllerHelper.deleteExpense(expenseId, expenseService);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/claimants")
    public ResponseEntity<List<User>> getClaimants() {
        try {
            return new ResponseEntity(controllerHelper.users(userServiceNew), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public ResponseEntity<List<TransactionCategory>> getCategorys(@RequestParam("categoryName") String queryString) {
        try {
            System.out.println("queryString=" + queryString);
            List<TransactionCategory> transactionCategoryList = transactionCategoryService.findAllTransactionCategoryByTransactionType(TRANSACTION_TYPE_EXPENSE, queryString);
            return new ResponseEntity(controllerHelper.completeCategory(transactionCategoryList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/currencys")
    public ResponseEntity<List<Currency>> getCurrencys(@RequestParam("currency") String queryString) {
        try {
            return new ResponseEntity(controllerHelper.completeCurrency(queryString, currencyService), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/projects")
    public ResponseEntity<List<Project>> getProjects(@RequestParam("projectName") String queryString) {
        try {
            return new ResponseEntity(controllerHelper.projects(queryString, projectService), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
