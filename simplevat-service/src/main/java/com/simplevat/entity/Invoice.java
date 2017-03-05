package com.simplevat.entity;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "INVOICE")
@Data
public class Invoice implements Serializable {

    private static final long serialVersionUID = -8324261801367612269L;
    
    @Id
    @Column(name = "INVOICE_ID")
    private int invoiceId;
    @Basic
    @Column(name = "INVOICE_REFERENCE_NUMBER")
    private String invoiceReferenceNumber;
    @Basic
    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;
    @Basic
    @Column(name = "INVOICE_DUE_IN_DAYS")
    private Integer invoiceDueOn;
    @Basic
    @Column(name = "INVOICE_TEXT")
    private String invoiceText;
    @Basic
    @Column(name = "INVOICE_DISCOUNT")
    private BigDecimal invoiceDiscount;
    @Basic
    @Column(name = "CONTACT_FULL_NAME")
    private String contactFullName;
    @Basic
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;
    @Basic
    @Column(name = "CONTACT_ID")
    private Integer contactId;
    @Basic
    @Column(name = "PROJECT_ID")
    private Integer projectId;
    @Basic
    @Column(name = "LANGUAGE_CODE")
    private Integer languageCode;
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
//    private Contact contactByContactId;
//    private Project projectByProjectId;
//    private Language languageByLanguageCode;
//    private Currency currencyByCurrencyCode;
//    private Collection<InvoiceLineItem> invoiceLineItemsByInvoiceId;

}
