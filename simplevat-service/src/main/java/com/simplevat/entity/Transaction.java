package com.simplevat.entity;

import com.simplevat.entity.BankAccount;
import com.simplevat.entity.Project;
import com.simplevat.entity.TransactionCategory;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTON")
@Data
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID")
    private int transactionId;
    @Basic
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;
    @Basic
    @Column(name = "TRANSACTION_DESCRIPTION")
    private String transactionDescription;
    @Basic
    @Column(name = "TRANSACTION_AMOUNT")
    private BigDecimal transactionAmount;
    @Basic
    @Column(name = "TRANSACTION_TYPE_CODE")
    private Integer transactionTypeCode;
    @Basic
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    @Basic
    @Column(name = "DEBIT_CREDIT_FLAG")
    private Character debitCreditFlag;
    @Basic
    @Column(name = "EXPLAINED_PROJECT_ID")
    private Integer explainedProjectId;
    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_CATEGORY_CODE")
    private Integer explainedTransactionCategoryCode;
    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_DESCRIPTION")
    private String explainedTransactionDescription;
    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_ATTACHEMENT_DESCRIPTION")
    private String explainedTransactionAttachementDescription;
    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_ATTACHEMENT_PATH")
    private String explainedTransactionAttachementPath;
    @Basic
    @Column(name = "BANK_ACCOUNT_ID")
    private Integer bankAccountId;
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
//    private TransactionType transactionTypeByTransactionTypeCode;
//    private Project projectByExplainedProjectId;
//    private TransactionCategory transactionCategoryByExplainedTransactionCategoryCode;
//    private BankAccount bankAccountByBankAccountId;

}
