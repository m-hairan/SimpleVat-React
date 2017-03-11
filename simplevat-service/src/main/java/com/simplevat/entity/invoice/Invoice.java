package com.simplevat.entity.invoice;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Project;
import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Data
@Entity
@Table(name = "INVOICE")
public class Invoice implements Serializable {

    private static final long serialVersionUID = -8324261801367612269L;

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "INVOICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;

    @Column(name = "INVOICE_REFERENCE_NUMBER")
    private String invoiceReferenceNumber;

    @Column(name = "INVOICE_DATE")
    @Temporal(TemporalType.DATE)
    private Calendar invoiceDate;

    @Column(name = "INVOICE_DUE_ON")
    private Integer invoiceDueOn;

    @Column(name = "INVOICE_TEXT")
    private String invoiceText;

    @Enumerated(EnumType.STRING)
    @Column(name = "INVOICE_DISCOUNT_TYPE")
    private DiscountType invoiceDiscountType;

    @Column(name = "INVOICE_DISCOUNT")
    private BigDecimal invoiceDiscount;

    @Column(name = "CONTACT_FULL_NAME")
    private String contactFullName;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;

    @Column(name = "LANGUAGE_CODE")
    private Integer languageCode;

    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Calendar createdDate;

//    @Column(name = "LAST_UPDATED_BY")
//    private Integer lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE_DATE")
    private Calendar lastUpdateDate;

    @Column(name = "DELETE_FLAG")
    private Character deleteFlag;

    @Column(name = "VERSION_NUMBER")
    private int versionNumber;

    @ManyToOne
    private Contact invoiceContact;

    @ManyToOne
    private Project invoiceProject;

//    private Language languageByLanguageCode;
//    private Currency currencyByCurrencyCode;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<InvoiceLineItem> invoiceLineItems;

}
