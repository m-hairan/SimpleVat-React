package org.simplevat.model;

import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.simplevat.entity.expense.ExpenseType;

@Getter
@Setter
@RequiredArgsConstructor
public class ExpenseModel {
    
    @Nonnull
    private ExpenseType type;
    
    @Nonnull
    private Double totalExpense;
    
}
