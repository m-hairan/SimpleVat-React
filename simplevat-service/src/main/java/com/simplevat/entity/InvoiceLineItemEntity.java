package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "INVOICE_LINE_ITEM", schema = "simplevat", catalog = "")
public class InvoiceLineItemEntity {
    private int invoiceLineItemId;
    private Integer invoiceLineItemQuantity;
    private String invoiceLineItemDescription;
    private BigDecimal invoiceLineItemUnitPrice;
    private BigDecimal invoiceLineItemVat;
    private Integer invoiceId;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private InvoiceEntity invoiceByInvoiceId;

    @Id
    @Column(name = "INVOICE_LINE_ITEM_ID")
    public int getInvoiceLineItemId() {
        return invoiceLineItemId;
    }

    public void setInvoiceLineItemId(int invoiceLineItemId) {
        this.invoiceLineItemId = invoiceLineItemId;
    }

    @Basic
    @Column(name = "INVOICE_LINE_ITEM_QUANTITY")
    public Integer getInvoiceLineItemQuantity() {
        return invoiceLineItemQuantity;
    }

    public void setInvoiceLineItemQuantity(Integer invoiceLineItemQuantity) {
        this.invoiceLineItemQuantity = invoiceLineItemQuantity;
    }

    @Basic
    @Column(name = "INVOICE_LINE_ITEM_DESCRIPTION")
    public String getInvoiceLineItemDescription() {
        return invoiceLineItemDescription;
    }

    public void setInvoiceLineItemDescription(String invoiceLineItemDescription) {
        this.invoiceLineItemDescription = invoiceLineItemDescription;
    }

    @Basic
    @Column(name = "INVOICE_LINE_ITEM_UNIT_PRICE")
    public BigDecimal getInvoiceLineItemUnitPrice() {
        return invoiceLineItemUnitPrice;
    }

    public void setInvoiceLineItemUnitPrice(BigDecimal invoiceLineItemUnitPrice) {
        this.invoiceLineItemUnitPrice = invoiceLineItemUnitPrice;
    }

    @Basic
    @Column(name = "INVOICE_LINE_ITEM_VAT")
    public BigDecimal getInvoiceLineItemVat() {
        return invoiceLineItemVat;
    }

    public void setInvoiceLineItemVat(BigDecimal invoiceLineItemVat) {
        this.invoiceLineItemVat = invoiceLineItemVat;
    }

    @Basic
    @Column(name = "INVOICE_ID")
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Basic
    @Column(name = "CREATED_BY")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "LAST_UPDATED_BY")
    public Date getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Date lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Basic
    @Column(name = "DELETE_FLAG")
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Basic
    @Column(name = "VERSION_NUMBER")
    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceLineItemEntity that = (InvoiceLineItemEntity) o;

        if (invoiceLineItemId != that.invoiceLineItemId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (invoiceLineItemQuantity != null ? !invoiceLineItemQuantity.equals(that.invoiceLineItemQuantity) : that.invoiceLineItemQuantity != null)
            return false;
        if (invoiceLineItemDescription != null ? !invoiceLineItemDescription.equals(that.invoiceLineItemDescription) : that.invoiceLineItemDescription != null)
            return false;
        if (invoiceLineItemUnitPrice != null ? !invoiceLineItemUnitPrice.equals(that.invoiceLineItemUnitPrice) : that.invoiceLineItemUnitPrice != null)
            return false;
        if (invoiceLineItemVat != null ? !invoiceLineItemVat.equals(that.invoiceLineItemVat) : that.invoiceLineItemVat != null)
            return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastUpdatedBy != null ? !lastUpdatedBy.equals(that.lastUpdatedBy) : that.lastUpdatedBy != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceLineItemId;
        result = 31 * result + (invoiceLineItemQuantity != null ? invoiceLineItemQuantity.hashCode() : 0);
        result = 31 * result + (invoiceLineItemDescription != null ? invoiceLineItemDescription.hashCode() : 0);
        result = 31 * result + (invoiceLineItemUnitPrice != null ? invoiceLineItemUnitPrice.hashCode() : 0);
        result = 31 * result + (invoiceLineItemVat != null ? invoiceLineItemVat.hashCode() : 0);
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID")
    public InvoiceEntity getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(InvoiceEntity invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }
}
