package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "TRANSACTION_TYPE_NAME")
    private String transactionTypeName;

    @Column(name = "TRANSACTION_TYPE_DESCRIPTION")
    private String transactionTypeDescription;

    @Column(name = "DEBIT_CREDIT_FLAG")
    private Character DEBIT_CREDIT_FLAG;

    @Column(name = "DEFAULT_FLAG")
    private Character DEFAULT_FLAG;

    @Column(name = "ORDER_SEQUENCE")
    private Integer ORDER_SEQUENCE;

    @Column(name = "CREATED_BY")
    private Integer CREATED_BY;

    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime CREATED_DATE;

    @Column(name = "LAST_UPDATED_BY")
    private Integer LAST_UPDATED_BY;

    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime LAST_UPDATE_DATE;

    @Column(name = "VERSION_NUMBER")
    private Integer VERSION_NUMBER;

//    private Collection<Expense> expensesByTransactionTypeCode;
//    private Collection<TransactionCategory> transactionCategoriesByTransactionTypeCode;
//    private Collection<Transaction> transactonsByTransactionTypeCode;

}
