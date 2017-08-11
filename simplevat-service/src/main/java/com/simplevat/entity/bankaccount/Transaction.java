package com.simplevat.entity.bankaccount;

import com.simplevat.entity.Project;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_TYPE_CODE")
    private TransactionType transactionType;
    
    @Basic
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    @Basic
    @Column(name = "DEBIT_CREDIT_FLAG")
    private Character debitCreditFlag;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPLAINED_PROJECT_ID")
    private Project project;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPLAINED_TRANSACTION_CATEGORY_CODE")
    private TransactionCategory explainedTransactionCategory;
    
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPLANATION_STATUS_CODE")
    private TransactionStatus transactionStatus;
    
    @Column(name="CURRENT_BALANCE")
    private BigDecimal currentBalance;
    
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
    private Boolean deleteFlag = Boolean.FALSE;
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;
    
    @PrePersist
    public void updateDates() {
        createdDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updateLastUpdatedDate() {
        lastUpdateDate = LocalDateTime.now();
    }

	
    
}
