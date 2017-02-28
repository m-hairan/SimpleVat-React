package org.simplevat.entity.expense;

import javax.annotation.Nonnull;

import org.simplevat.entity.domain.AbstractDomain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense extends AbstractDomain {

    private static final long serialVersionUID = -4396218881161673703L;

    @Nonnull
    private Long expenseId;

    @Nonnull
    private ExpenseType type;

    @Nonnull
    private Integer year;

    @Nonnull
    private String description;

    @Nonnull
    private Double amount;

}
