package com.simplevat.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "EXPENSE_LINE_ITEM")
@Data
public class ExpenseLineItem implements Serializable {

    private static final long serialVersionUID = 848122185643690684L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPENSE_LINE_ITEM_ID")
    private int expenseLineItemId;

    @Basic
    @Column(name = "EXPENSE_LINE_ITEM_QUANTITY")
    private Integer expenseLineItemQuantity;

    @Basic
    @Column(name = "EXPENSE_LINE_ITEM_DESCRIPTION")
    private String expenseLineItemDescription;

    @Basic
    @Column(name = "EXPENSE_LINE_ITEM_UNIT_PRICE")
    private BigDecimal expenseLineItemUnitPrice;

    @Basic
    @Column(name = "EXPENSE_LINE_ITEM_VAT")
    private BigDecimal expenseLineItemVat;

    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;

    @Basic
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber = 0;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;

    
}
