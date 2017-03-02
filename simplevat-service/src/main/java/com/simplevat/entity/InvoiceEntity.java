package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "INVOICE", schema = "simplevat", catalog = "")
public class InvoiceEntity {
    private int invoiceId;
    private String invoiceReferenceNumber;
    private Date invoiceDate;
    private Integer invoiceDueOn;
    private String invoiceText;
    private BigDecimal invoiceDiscount;
    private String contactFullName;
    private String projectName;
    private String contractPoNumber;
    private Integer contactId;
    private Integer projectId;
    private Integer languageCode;
    private Integer currencyCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private ContactEntity contactByContactId;
    private ProjectEntity projectByProjectId;
    private Language languageByLanguageCode;
    private CurrencyEntity currencyByCurrencyCode;
    private Collection<InvoiceLineItemEntity> invoiceLineItemsByInvoiceId;

    @Id
    @Column(name = "INVOICE_ID")
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Basic
    @Column(name = "INVOICE_REFERENCE_NUMBER")
    public String getInvoiceReferenceNumber() {
        return invoiceReferenceNumber;
    }

    public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
        this.invoiceReferenceNumber = invoiceReferenceNumber;
    }

    @Basic
    @Column(name = "INVOICE_DATE")
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Basic
    @Column(name = "INVOICE_DUE_ON")
    public Integer getInvoiceDueOn() {
        return invoiceDueOn;
    }

    public void setInvoiceDueOn(Integer invoiceDueOn) {
        this.invoiceDueOn = invoiceDueOn;
    }

    @Basic
    @Column(name = "INVOICE_TEXT")
    public String getInvoiceText() {
        return invoiceText;
    }

    public void setInvoiceText(String invoiceText) {
        this.invoiceText = invoiceText;
    }

    @Basic
    @Column(name = "INVOICE_DISCOUNT")
    public BigDecimal getInvoiceDiscount() {
        return invoiceDiscount;
    }

    public void setInvoiceDiscount(BigDecimal invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    @Basic
    @Column(name = "CONTACT_FULL_NAME")
    public String getContactFullName() {
        return contactFullName;
    }

    public void setContactFullName(String contactFullName) {
        this.contactFullName = contactFullName;
    }

    @Basic
    @Column(name = "PROJECT_NAME")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "CONTRACT_PO_NUMBER")
    public String getContractPoNumber() {
        return contractPoNumber;
    }

    public void setContractPoNumber(String contractPoNumber) {
        this.contractPoNumber = contractPoNumber;
    }

    @Basic
    @Column(name = "CONTACT_ID")
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "PROJECT_ID")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "LANGUAGE_CODE")
    public Integer getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(Integer languageCode) {
        this.languageCode = languageCode;
    }

    @Basic
    @Column(name = "CURRENCY_CODE")
    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
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

        InvoiceEntity that = (InvoiceEntity) o;

        if (invoiceId != that.invoiceId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (invoiceReferenceNumber != null ? !invoiceReferenceNumber.equals(that.invoiceReferenceNumber) : that.invoiceReferenceNumber != null)
            return false;
        if (invoiceDate != null ? !invoiceDate.equals(that.invoiceDate) : that.invoiceDate != null) return false;
        if (invoiceDueOn != null ? !invoiceDueOn.equals(that.invoiceDueOn) : that.invoiceDueOn != null) return false;
        if (invoiceText != null ? !invoiceText.equals(that.invoiceText) : that.invoiceText != null) return false;
        if (invoiceDiscount != null ? !invoiceDiscount.equals(that.invoiceDiscount) : that.invoiceDiscount != null)
            return false;
        if (contactFullName != null ? !contactFullName.equals(that.contactFullName) : that.contactFullName != null)
            return false;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        if (contractPoNumber != null ? !contractPoNumber.equals(that.contractPoNumber) : that.contractPoNumber != null)
            return false;
        if (contactId != null ? !contactId.equals(that.contactId) : that.contactId != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (languageCode != null ? !languageCode.equals(that.languageCode) : that.languageCode != null) return false;
        if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
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
        int result = invoiceId;
        result = 31 * result + (invoiceReferenceNumber != null ? invoiceReferenceNumber.hashCode() : 0);
        result = 31 * result + (invoiceDate != null ? invoiceDate.hashCode() : 0);
        result = 31 * result + (invoiceDueOn != null ? invoiceDueOn.hashCode() : 0);
        result = 31 * result + (invoiceText != null ? invoiceText.hashCode() : 0);
        result = 31 * result + (invoiceDiscount != null ? invoiceDiscount.hashCode() : 0);
        result = 31 * result + (contactFullName != null ? contactFullName.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (contractPoNumber != null ? contractPoNumber.hashCode() : 0);
        result = 31 * result + (contactId != null ? contactId.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID")
    public ContactEntity getContactByContactId() {
        return contactByContactId;
    }

    public void setContactByContactId(ContactEntity contactByContactId) {
        this.contactByContactId = contactByContactId;
    }

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    public ProjectEntity getProjectByProjectId() {
        return projectByProjectId;
    }

    public void setProjectByProjectId(ProjectEntity projectByProjectId) {
        this.projectByProjectId = projectByProjectId;
    }

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_CODE", referencedColumnName = "LANGUAGE_CODE")
    public Language getLanguageByLanguageCode() {
        return languageByLanguageCode;
    }

    public void setLanguageByLanguageCode(Language languageByLanguageCode) {
        this.languageByLanguageCode = languageByLanguageCode;
    }

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    public CurrencyEntity getCurrencyByCurrencyCode() {
        return currencyByCurrencyCode;
    }

    public void setCurrencyByCurrencyCode(CurrencyEntity currencyByCurrencyCode) {
        this.currencyByCurrencyCode = currencyByCurrencyCode;
    }

    @OneToMany(mappedBy = "invoiceByInvoiceId")
    public Collection<InvoiceLineItemEntity> getInvoiceLineItemsByInvoiceId() {
        return invoiceLineItemsByInvoiceId;
    }

    public void setInvoiceLineItemsByInvoiceId(Collection<InvoiceLineItemEntity> invoiceLineItemsByInvoiceId) {
        this.invoiceLineItemsByInvoiceId = invoiceLineItemsByInvoiceId;
    }
}
