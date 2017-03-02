package org.simplevat.service.expense;

import java.util.List;
import javax.annotation.Nonnull;
import org.simplevat.entity.expense.Expense;
import org.simplevat.entity.expense.ExpenseType;

public interface ExpenseService {

    @Nonnull
    public List<Expense> getExpenseList();
    
    @Nonnull
    public List<Expense> getTypeExpenseList(ExpenseType type);
}
