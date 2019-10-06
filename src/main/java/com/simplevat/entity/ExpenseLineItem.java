package com.simplevat.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import org.hibernate.annotations.ColumnDefault;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPENSE_LINE_ITEM_PRODUCT_SERVICE_ID")
    private Product expenseLineItemProductService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPENSE_LINE_ITEM_VAT_ID")
    private VatCategory expenseLineItemVat;

    @Column(name = "DELETE_FLAG")
    @ColumnDefault(value = "0")
    @Basic(optional = false)
    private Boolean deleteFlag = Boolean.FALSE;

    @Column(name = "VERSION_NUMBER")
    @ColumnDefault(value = "1")
    @Basic(optional = false)
    @Version
    private Integer versionNumber;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;
    
    @Column(name = "EXPENSE_PRODUCT_NAME")
    private String productName;

}
