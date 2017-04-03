package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime transactionDate;
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
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;
    @Basic
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
//    private TransactionType transactionTypeByTransactionTypeCode;
//    private Project projectByExplainedProjectId;
//    private TransactionCategory transactionCategoryByExplainedTransactionCategoryCode;
//    private BankAccount bankAccountByBankAccountId;

}
