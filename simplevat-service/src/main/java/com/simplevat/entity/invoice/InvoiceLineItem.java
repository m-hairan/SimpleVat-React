package com.simplevat.entity.invoice;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "INVOICE_LINE_ITEM")
@Data
public class InvoiceLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_LINE_ITEM_ID")
    private int invoiceLineItemId;
    @Basic
    @Column(name = "INVOICE_LINE_ITEM_QUANTITY")
    private Integer invoiceLineItemQuantity;
    @Basic
    @Column(name = "INVOICE_LINE_ITEM_DESCRIPTION")
    private String invoiceLineItemDescription;
    @Basic
    @Column(name = "INVOICE_LINE_ITEM_UNIT_PRICE")
    private BigDecimal invoiceLineItemUnitPrice;
    @Basic
    @Column(name = "INVOICE_LINE_ITEM_VAT")
    private BigDecimal invoiceLineItemVat;
    @Basic
    @Column(name = "INVOICE_ID")
    private Integer invoiceId;
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
//    private Invoice invoiceByInvoiceId;
}
