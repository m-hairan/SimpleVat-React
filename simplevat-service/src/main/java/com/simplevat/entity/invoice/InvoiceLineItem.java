package com.simplevat.entity.invoice;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "INVOICE_LINE_ITEM")
@Data
public class InvoiceLineItem implements Serializable {

    private static final long serialVersionUID = 848122185643690684L;

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
    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Calendar createdDate;

    @Basic
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DATE")
    private Calendar lastUpdateDate;

    @Basic
    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag = Boolean.FALSE;

    @Basic
    @Column(name = "VERSION_NUMBER")
    private Integer versionNumber = 0;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;
}
