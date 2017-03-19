package com.simplevat.service.impl;

import com.simplevat.dao.ExpenseDao;
import com.simplevat.entity.Expense;
import com.simplevat.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("expenseService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    public ExpenseDao expenseDao;

    public Expense saveExpense(Expense expense) {
        return expenseDao.saveExpense(expense);
    }

    public List<Expense> getExpenseList() {
        // TODO Auto-generated method stub
        return null;
    }


}
