package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTION_CATEGORY")
@Data
public class TransactionCategory {
    @Id
    @Column(name = "TRANSACTION_CATEGORY_CODE")
    private int transactionCategoryCode;
    @Basic
    @Column(name = "TRANSACTION_CATEGORY_NAME")
    private String transactionCategoryName;
    @Basic
    @Column(name = "TRANSACTION_CATEGORY_DESCRIPTION")
    private String transactionCategoryDescription;
    @Basic
    @Column(name = "TRANSACTION_TYPE_CODE")
    private int transactionTypeCode;
    @Basic
    @Column(name = "PARENT_TRANSACTION_CATEGORY_CODE")
    private Integer parentTransactionCategoryCode;
    @Basic
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Basic
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Date lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Character deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private int versionNumber;
//    private Collection<Expense> expensesByTransactionCategoryCode;
//    private TransactionType transactionTypeByTransactionTypeCode;
//    private TransactionCategory transactionCategoryByParentTransactionCategoryCode;
//    private Collection<TransactionCategory> transactionCategoriesByTransactionCategoryCode;
//    private Collection<Transaction> transactonsByTransactionCategoryCode;


}
