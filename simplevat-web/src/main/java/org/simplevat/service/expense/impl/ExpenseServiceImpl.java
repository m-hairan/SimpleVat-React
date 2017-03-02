package org.simplevat.service.expense.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.simplevat.dao.expense.ExpenseDao;
import org.simplevat.entity.expense.Expense;
import org.simplevat.entity.expense.ExpenseType;
import org.simplevat.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    public ExpenseDao expenseDao;
    
    @Override
    @Nonnull
    public List<Expense> getExpenseList() {
        return expenseDao.getExpenseList();
    }

    @Override
    @Nonnull
    public List<Expense> getTypeExpenseList(ExpenseType type) {
        
        List<Expense> typeExpenseList = new ArrayList<Expense>();
        for(Expense e : expenseDao.getExpenseList()){
            if(type.equals(e.getType())){
                typeExpenseList.add(e);
            }
        }
        return typeExpenseList;
    }

}
