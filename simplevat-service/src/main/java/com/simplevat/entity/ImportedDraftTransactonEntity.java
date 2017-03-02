package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "IMPORTED_DRAFT_TRANSACTON", schema = "simplevat", catalog = "")
public class ImportedDraftTransactonEntity {
    private int importedTransactionId;
    private Date importedTransactionDate;
    private String importedTransactionDescription;
    private BigDecimal importedTransactionAmount;
    private String importedDebitCreditFlag;
    private Integer bankAccountId;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private BankAccountEntity bankAccountByBankAccountId;

    @Id
    @Column(name = "IMPORTED_TRANSACTION_ID")
    public int getImportedTransactionId() {
        return importedTransactionId;
    }

    public void setImportedTransactionId(int importedTransactionId) {
        this.importedTransactionId = importedTransactionId;
    }

    @Basic
    @Column(name = "IMPORTED_TRANSACTION_DATE")
    public Date getImportedTransactionDate() {
        return importedTransactionDate;
    }

    public void setImportedTransactionDate(Date importedTransactionDate) {
        this.importedTransactionDate = importedTransactionDate;
    }

    @Basic
    @Column(name = "IMPORTED_TRANSACTION_DESCRIPTION")
    public String getImportedTransactionDescription() {
        return importedTransactionDescription;
    }

    public void setImportedTransactionDescription(String importedTransactionDescription) {
        this.importedTransactionDescription = importedTransactionDescription;
    }

    @Basic
    @Column(name = "IMPORTED_TRANSACTION_AMOUNT")
    public BigDecimal getImportedTransactionAmount() {
        return importedTransactionAmount;
    }

    public void setImportedTransactionAmount(BigDecimal importedTransactionAmount) {
        this.importedTransactionAmount = importedTransactionAmount;
    }

    @Basic
    @Column(name = "IMPORTED_DEBIT_CREDIT_FLAG")
    public String getImportedDebitCreditFlag() {
        return importedDebitCreditFlag;
    }

    public void setImportedDebitCreditFlag(String importedDebitCreditFlag) {
        this.importedDebitCreditFlag = importedDebitCreditFlag;
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

        ImportedDraftTransactonEntity that = (ImportedDraftTransactonEntity) o;

        if (importedTransactionId != that.importedTransactionId) return false;
        if (versionNumber != that.versionNumber) return false;
        if (importedTransactionDate != null ? !importedTransactionDate.equals(that.importedTransactionDate) : that.importedTransactionDate != null)
            return false;
        if (importedTransactionDescription != null ? !importedTransactionDescription.equals(that.importedTransactionDescription) : that.importedTransactionDescription != null)
            return false;
        if (importedTransactionAmount != null ? !importedTransactionAmount.equals(that.importedTransactionAmount) : that.importedTransactionAmount != null)
            return false;
        if (importedDebitCreditFlag != null ? !importedDebitCreditFlag.equals(that.importedDebitCreditFlag) : that.importedDebitCreditFlag != null)
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
        int result = importedTransactionId;
        result = 31 * result + (importedTransactionDate != null ? importedTransactionDate.hashCode() : 0);
        result = 31 * result + (importedTransactionDescription != null ? importedTransactionDescription.hashCode() : 0);
        result = 31 * result + (importedTransactionAmount != null ? importedTransactionAmount.hashCode() : 0);
        result = 31 * result + (importedDebitCreditFlag != null ? importedDebitCreditFlag.hashCode() : 0);
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
    @JoinColumn(name = "BANK_ACCOUNT_ID", referencedColumnName = "BANK_ACCOUNT_ID")
    public BankAccountEntity getBankAccountByBankAccountId() {
        return bankAccountByBankAccountId;
    }

    public void setBankAccountByBankAccountId(BankAccountEntity bankAccountByBankAccountId) {
        this.bankAccountByBankAccountId = bankAccountByBankAccountId;
    }
}
