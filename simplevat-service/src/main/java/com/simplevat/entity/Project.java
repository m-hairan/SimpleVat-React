package com.simplevat.entity;

import com.simplevat.entity.converter.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "PROJECT")
@Data
public class Project implements Serializable{
    @Id
    @Column(name = "PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;
    @Basic
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Basic
    @Column(name = "PROJECT_BUDGET")
    private BigDecimal projectBudget;
    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;
    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_LANGUAGE_CODE")
    private Language invoiceLanguageCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_CODE")
    private Currency currency;

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
    private LocalDateTime lastUpdateDate;
    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;
    @Basic
    @Version
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber = 1;
//    private Collection<Expense> expensesByProjectId;
//    private Collection<Invoice> invoicesByProjectId;
//    private Contact contactByContactId;
//    private Language languageByInvoiceLanguageCode;
//    private Currency currencyByCurrencyCode;
//    private Collection<Transaction> transactonsByProjectId;

}
