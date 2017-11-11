package com.simplevat.entity.invoice;

import com.simplevat.entity.Contact;
import com.simplevat.entity.Currency;
import com.simplevat.entity.DocumentTemplate;
import com.simplevat.entity.Project;
import com.simplevat.entity.converter.DateConverter;
import java.io.Serializable;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Data
@Entity
@Table(name = "INVOICE")
@NamedQueries({
    @NamedQuery(name = "Invoice.searchInvoices",
            query = "from Invoice i where i.deleteFlag = false order by i.lastUpdateDate desc")
})
public class Invoice implements Serializable {

    private static final long serialVersionUID = -8324261801367612269L;

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "INVOICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    @Column(name = "INVOICE_REFERENCE_NUMBER")
    private String invoiceReferenceNumber;

    @Column(name = "INVOICE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime invoiceDate;

    @Basic(optional = false)
    @Column(name = "INVOICE_DUE_ON")
    @ColumnDefault(value = "0")
    private Integer invoiceDueOn;

    @Column(name = "INVOICE_DUE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime invoiceDueDate;

    @Column(name = "INVOICE_TEXT")
    private String invoiceText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCOUNT_TYPE_CODE")
    private DiscountType discountType;

    @Column(name = "INVOICE_DISCOUNT")
    @ColumnDefault(value = "0.00")
    private BigDecimal invoiceDiscount;

    @Column(name = "CONTRACT_PO_NUMBER")
    private String contractPoNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_CODE")
    private Currency currency;

    @Column(name = "CREATED_BY")
    @Basic(optional = false)
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Basic(optional = false)
    @Convert(converter = DateConverter.class)
    private LocalDateTime createdDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdateBy;

    @Column(name = "LAST_UPDATE_DATE")
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateDate;

    @Column(name = "DELETE_FLAG")
    @ColumnDefault(value = "0")
    @Basic(optional = false)
    private Boolean deleteFlag = Boolean.FALSE;

    @Column(name = "VERSION_NUMBER")
    @ColumnDefault(value = "1")
    @Basic(optional = false)
    @Version
    private Integer versionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_ID")
    private Contact invoiceContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project invoiceProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_TEMPLATE_ID")
    private DocumentTemplate documentTemplate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "invoice", orphanRemoval = true)
    private Collection<InvoiceLineItem> invoiceLineItems;

    @Column(name = "INVOICE_AMOUNT")
    @ColumnDefault(value = "0.00")
    private BigDecimal invoiceAmount;

    @Column(name = "DUE_AMOUNT")
    @ColumnDefault(value = "0.00")
    private BigDecimal dueAmount;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PAYMENTMODE")
    private Integer paymentMode;

    @Nonnull
    public Collection<InvoiceLineItem> getInvoiceLineItems() {
        return (invoiceLineItems == null) ? (invoiceLineItems = new ArrayList<>()) : invoiceLineItems;
    }

    public void setInvoiceLineItems(@Nonnull final Collection<InvoiceLineItem> invoiceLineItems) {

        final Collection<InvoiceLineItem> thisInvoiceLineItems = getInvoiceLineItems();
        thisInvoiceLineItems.clear();
        thisInvoiceLineItems.addAll(invoiceLineItems);
    }

    @PrePersist
    public void updateDates() {
        createdDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updateLastUpdatedDate() {
        lastUpdateDate = LocalDateTime.now();
    }

}
