package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "IMPORTED_DRAFT_TRANSACTON")
@Data
public class ImportedDraftTransacton {
    @Id
    @Column(name = "IMPORTED_TRANSACTION_ID")
    private int importedTransactionId;
    @Basic
    @Column(name = "IMPORTED_TRANSACTION_DATE")
    private Date importedTransactionDate;
    @Basic
    @Column(name = "IMPORTED_TRANSACTION_DESCRIPTION")
    private String importedTransactionDescription;
    @Basic
    @Column(name = "IMPORTED_TRANSACTION_AMOUNT")
    private BigDecimal importedTransactionAmount;
    @Basic
    @Column(name = "IMPORTED_DEBIT_CREDIT_FLAG")
    private Character importedDebitCreditFlag;
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
//    private BankAccount bankAccountByBankAccountId;

}
