package com.simplevat.entity;

import com.simplevat.entity.Expense;
import com.simplevat.entity.Transaction;
import com.simplevat.entity.TransactionCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTION_TYPE")
@Data
public class TransactionType {
    @Id
    @Column(name = "TRANSACTION_TYPE_CODE")
    private int transactionTypeCode;
    @Basic
    @Column(name = "TRANSACTION_TYPE_NAME")
    private String transactionTypeName;
    @Basic
    @Column(name = "TRANSACTION_TYPE_DESCRIPTION")
    private String transactionTypeDescription;
    @Basic
    @Column(name = "TRANSACTION_CREDIT_DEBIT")
    private String transactionCreditDebit;
//    private Collection<Expense> expensesByTransactionTypeCode;
//    private Collection<TransactionCategory> transactionCategoriesByTransactionTypeCode;
//    private Collection<Transaction> transactonsByTransactionTypeCode;

}
