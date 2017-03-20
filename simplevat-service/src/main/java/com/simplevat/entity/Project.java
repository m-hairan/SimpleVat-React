package com.simplevat.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "PROJECT")
@Data
public class Project {
    @Id
    @Column(name = "PROJECT_ID")
    private int projectId;
    @Basic
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Basic
    @Column(name = "PROJECT_BUDGET")
    private BigDecimal projectBudget;
    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;
    @Basic
    @Column(name = "CONTACT_ID")
    private Integer contactId;
    @Basic
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;
    @Basic
    @Column(name = "INVOICE_LANGUAGE_CODE")
    private Integer invoiceLanguageCode;
    @Basic
    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;
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
//    private Collection<Expense> expensesByProjectId;
//    private Collection<Invoice> invoicesByProjectId;
//    private Contact contactByContactId;
//    private Language languageByInvoiceLanguageCode;
//    private Currency currencyByCurrencyCode;
//    private Collection<Transaction> transactonsByProjectId;

}
