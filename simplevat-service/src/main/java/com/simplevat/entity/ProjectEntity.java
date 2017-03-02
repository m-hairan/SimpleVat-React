package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "PROJECT", schema = "simplevat", catalog = "")
public class ProjectEntity {
    private int projectId;
    private String projectName;
    private BigDecimal projectBudget;
    private String contractPoNumber;
    private Integer contactId;
    private String vatRegistrationNumber;
    private Integer invoiceLanguageCode;
    private Integer currencyCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private Collection<ExpenseEntity> expensesByProjectId;
    private Collection<InvoiceEntity> invoicesByProjectId;
    private ContactEntity contactByContactId;
    private LanguageEntity languageByInvoiceLanguageCode;
    private CurrencyEntity currencyByCurrencyCode;
    private Collection<TransactionEntity> transactonsByProjectId;

    @Id
    @Column(name = "PROJECT_ID")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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
    @Column(name = "PROJECT_BUDGET")
    public BigDecimal getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(BigDecimal projectBudget) {
        this.projectBudget = projectBudget;
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
    @Column(name = "VAT_REGISTRATION_NUMBER")
    public String getVatRegistrationNumber() {
        return vatRegistrationNumber;
    }

    public void setVatRegistrationNumber(String vatRegistrationNumber) {
        this.vatRegistrationNumber = vatRegistrationNumber;
    }

    @Basic
    @Column(name = "INVOICE_LANGUAGE_CODE")
    public Integer getInvoiceLanguageCode() {
        return invoiceLanguageCode;
    }

    public void setInvoiceLanguageCode(Integer invoiceLanguageCode) {
        this.invoiceLanguageCode = invoiceLanguageCode;
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

        ProjectEntity that = (ProjectEntity) o;

        if (projectId != that.projectId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        if (projectBudget != null ? !projectBudget.equals(that.projectBudget) : that.projectBudget != null)
            return false;
        if (contractPoNumber != null ? !contractPoNumber.equals(that.contractPoNumber) : that.contractPoNumber != null)
            return false;
        if (contactId != null ? !contactId.equals(that.contactId) : that.contactId != null) return false;
        if (vatRegistrationNumber != null ? !vatRegistrationNumber.equals(that.vatRegistrationNumber) : that.vatRegistrationNumber != null)
            return false;
        if (invoiceLanguageCode != null ? !invoiceLanguageCode.equals(that.invoiceLanguageCode) : that.invoiceLanguageCode != null)
            return false;
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
        int result = projectId;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectBudget != null ? projectBudget.hashCode() : 0);
        result = 31 * result + (contractPoNumber != null ? contractPoNumber.hashCode() : 0);
        result = 31 * result + (contactId != null ? contactId.hashCode() : 0);
        result = 31 * result + (vatRegistrationNumber != null ? vatRegistrationNumber.hashCode() : 0);
        result = 31 * result + (invoiceLanguageCode != null ? invoiceLanguageCode.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @OneToMany(mappedBy = "projectByProjectId")
    public Collection<ExpenseEntity> getExpensesByProjectId() {
        return expensesByProjectId;
    }

    public void setExpensesByProjectId(Collection<ExpenseEntity> expensesByProjectId) {
        this.expensesByProjectId = expensesByProjectId;
    }

    @OneToMany(mappedBy = "projectByProjectId")
    public Collection<InvoiceEntity> getInvoicesByProjectId() {
        return invoicesByProjectId;
    }

    public void setInvoicesByProjectId(Collection<InvoiceEntity> invoicesByProjectId) {
        this.invoicesByProjectId = invoicesByProjectId;
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
    @JoinColumn(name = "INVOICE_LANGUAGE_CODE", referencedColumnName = "LANGUAGE_CODE")
    public LanguageEntity getLanguageByInvoiceLanguageCode() {
        return languageByInvoiceLanguageCode;
    }

    public void setLanguageByInvoiceLanguageCode(LanguageEntity languageByInvoiceLanguageCode) {
        this.languageByInvoiceLanguageCode = languageByInvoiceLanguageCode;
    }

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    public CurrencyEntity getCurrencyByCurrencyCode() {
        return currencyByCurrencyCode;
    }

    public void setCurrencyByCurrencyCode(CurrencyEntity currencyByCurrencyCode) {
        this.currencyByCurrencyCode = currencyByCurrencyCode;
    }

    @OneToMany(mappedBy = "projectByExplainedProjectId")
    public Collection<TransactionEntity> getTransactonsByProjectId() {
        return transactonsByProjectId;
    }

    public void setTransactonsByProjectId(Collection<TransactionEntity> transactonsByProjectId) {
        this.transactonsByProjectId = transactonsByProjectId;
    }
}
