package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTION", schema = "simplevat", catalog = "")
public class TransactionEntity {
    private int transactionId;
    private Date transactionDate;
    private String transactionDescription;
    private BigDecimal transactionAmount;
    private Integer transactionTypeCode;
    private String receiptNumber;
    private String debitCreditFlag;
    private Integer explainedProjectId;
    private Integer explainedTransactionCategoryCode;
    private String explainedTransactionDescription;
    private String explainedTransactionAttachementDescription;
    private String explainedTransactionAttachementPath;
    private Integer bankAccountId;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private TransactionTypeEntity transactionTypeByTransactionTypeCode;
    private ProjectEntity projectByExplainedProjectId;
    private TransactionCategoryEntity transactionCategoryByExplainedTransactionCategoryCode;
    private BankAccountEntity bankAccountByBankAccountId;

    @Id
    @Column(name = "TRANSACTION_ID")
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "TRANSACTION_DATE")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Basic
    @Column(name = "TRANSACTION_DESCRIPTION")
    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    @Basic
    @Column(name = "TRANSACTION_AMOUNT")
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE_CODE")
    public Integer getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(Integer transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    @Basic
    @Column(name = "RECEIPT_NUMBER")
    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Basic
    @Column(name = "DEBIT_CREDIT_FLAG")
    public String getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(String debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
    }

    @Basic
    @Column(name = "EXPLAINED_PROJECT_ID")
    public Integer getExplainedProjectId() {
        return explainedProjectId;
    }

    public void setExplainedProjectId(Integer explainedProjectId) {
        this.explainedProjectId = explainedProjectId;
    }

    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_CATEGORY_CODE")
    public Integer getExplainedTransactionCategoryCode() {
        return explainedTransactionCategoryCode;
    }

    public void setExplainedTransactionCategoryCode(Integer explainedTransactionCategoryCode) {
        this.explainedTransactionCategoryCode = explainedTransactionCategoryCode;
    }

    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_DESCRIPTION")
    public String getExplainedTransactionDescription() {
        return explainedTransactionDescription;
    }

    public void setExplainedTransactionDescription(String explainedTransactionDescription) {
        this.explainedTransactionDescription = explainedTransactionDescription;
    }

    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_ATTACHEMENT_DESCRIPTION")
    public String getExplainedTransactionAttachementDescription() {
        return explainedTransactionAttachementDescription;
    }

    public void setExplainedTransactionAttachementDescription(String explainedTransactionAttachementDescription) {
        this.explainedTransactionAttachementDescription = explainedTransactionAttachementDescription;
    }

    @Basic
    @Column(name = "EXPLAINED_TRANSACTION_ATTACHEMENT_PATH")
    public String getExplainedTransactionAttachementPath() {
        return explainedTransactionAttachementPath;
    }

    public void setExplainedTransactionAttachementPath(String explainedTransactionAttachementPath) {
        this.explainedTransactionAttachementPath = explainedTransactionAttachementPath;
    }

    @Basic
    @Column(name = "BANK_ACCOUNT_ID")
    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
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

        TransactionEntity that = (TransactionEntity) o;

        if (transactionId != that.transactionId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;
        if (transactionDescription != null ? !transactionDescription.equals(that.transactionDescription) : that.transactionDescription != null)
            return false;
        if (transactionAmount != null ? !transactionAmount.equals(that.transactionAmount) : that.transactionAmount != null)
            return false;
        if (transactionTypeCode != null ? !transactionTypeCode.equals(that.transactionTypeCode) : that.transactionTypeCode != null)
            return false;
        if (receiptNumber != null ? !receiptNumber.equals(that.receiptNumber) : that.receiptNumber != null)
            return false;
        if (debitCreditFlag != null ? !debitCreditFlag.equals(that.debitCreditFlag) : that.debitCreditFlag != null)
            return false;
        if (explainedProjectId != null ? !explainedProjectId.equals(that.explainedProjectId) : that.explainedProjectId != null)
            return false;
        if (explainedTransactionCategoryCode != null ? !explainedTransactionCategoryCode.equals(that.explainedTransactionCategoryCode) : that.explainedTransactionCategoryCode != null)
            return false;
        if (explainedTransactionDescription != null ? !explainedTransactionDescription.equals(that.explainedTransactionDescription) : that.explainedTransactionDescription != null)
            return false;
        if (explainedTransactionAttachementDescription != null ? !explainedTransactionAttachementDescription.equals(that.explainedTransactionAttachementDescription) : that.explainedTransactionAttachementDescription != null)
            return false;
        if (explainedTransactionAttachementPath != null ? !explainedTransactionAttachementPath.equals(that.explainedTransactionAttachementPath) : that.explainedTransactionAttachementPath != null)
            return false;
        if (bankAccountId != null ? !bankAccountId.equals(that.bankAccountId) : that.bankAccountId != null)
            return false;
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
        int result = transactionId;
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        result = 31 * result + (transactionDescription != null ? transactionDescription.hashCode() : 0);
        result = 31 * result + (transactionAmount != null ? transactionAmount.hashCode() : 0);
        result = 31 * result + (transactionTypeCode != null ? transactionTypeCode.hashCode() : 0);
        result = 31 * result + (receiptNumber != null ? receiptNumber.hashCode() : 0);
        result = 31 * result + (debitCreditFlag != null ? debitCreditFlag.hashCode() : 0);
        result = 31 * result + (explainedProjectId != null ? explainedProjectId.hashCode() : 0);
        result = 31 * result + (explainedTransactionCategoryCode != null ? explainedTransactionCategoryCode.hashCode() : 0);
        result = 31 * result + (explainedTransactionDescription != null ? explainedTransactionDescription.hashCode() : 0);
        result = 31 * result + (explainedTransactionAttachementDescription != null ? explainedTransactionAttachementDescription.hashCode() : 0);
        result = 31 * result + (explainedTransactionAttachementPath != null ? explainedTransactionAttachementPath.hashCode() : 0);
        result = 31 * result + (bankAccountId != null ? bankAccountId.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_TYPE_CODE", referencedColumnName = "TRANSACTION_TYPE_CODE")
    public TransactionTypeEntity getTransactionTypeByTransactionTypeCode() {
        return transactionTypeByTransactionTypeCode;
    }

    public void setTransactionTypeByTransactionTypeCode(TransactionTypeEntity transactionTypeByTransactionTypeCode) {
        this.transactionTypeByTransactionTypeCode = transactionTypeByTransactionTypeCode;
    }

    @ManyToOne
    @JoinColumn(name = "EXPLAINED_PROJECT_ID", referencedColumnName = "PROJECT_ID")
    public ProjectEntity getProjectByExplainedProjectId() {
        return projectByExplainedProjectId;
    }

    public void setProjectByExplainedProjectId(ProjectEntity projectByExplainedProjectId) {
        this.projectByExplainedProjectId = projectByExplainedProjectId;
    }

    @ManyToOne
    @JoinColumn(name = "EXPLAINED_TRANSACTION_CATEGORY_CODE", referencedColumnName = "TRANSACTION_CATEGORY_CODE")
    public TransactionCategoryEntity getTransactionCategoryByExplainedTransactionCategoryCode() {
        return transactionCategoryByExplainedTransactionCategoryCode;
    }

    public void setTransactionCategoryByExplainedTransactionCategoryCode(TransactionCategoryEntity transactionCategoryByExplainedTransactionCategoryCode) {
        this.transactionCategoryByExplainedTransactionCategoryCode = transactionCategoryByExplainedTransactionCategoryCode;
    }

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID", referencedColumnName = "BANK_ACCOUNT_ID")
    public BankAccountEntity getBankAccountByBankAccountId() {
        return bankAccountByBankAccountId;
    }

    public void setBankAccountByBankAccountId(BankAccountEntity bankAccountByBankAccountId) {
        this.bankAccountByBankAccountId = bankAccountByBankAccountId;
    }
}
