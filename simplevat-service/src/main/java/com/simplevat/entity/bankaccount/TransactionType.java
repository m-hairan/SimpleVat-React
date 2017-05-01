package com.simplevat.entity.bankaccount;

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
    private Integer transactionTypeCode;

    @Column(name = "TRANSACTION_TYPE_NAME")
    private String transactionTypeName;

    @Column(name = "TRANSACTION_TYPE_DESCRIPTION")
    private String transactionTypeDescription;

    @Column(name = "DEBIT_CREDIT_FLAG")
    private Character debitCreditFlag;

    @Column(name = "DEFAULT_FLAG")
    private Character defaltFlag;

    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lateUpdatedBy;

    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime LastUpatedDate;
    
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;

    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber;

}
