package com.simplevat.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "TRANSACTION_CATEGORY", schema = "simplevat", catalog = "")
public class TransactionCategoryEntity {
    private int transactionCategoryCode;
    private String transactionCategoryName;
    private String transactionCategoryDescription;
    private int transactionTypeCode;
    private Integer parentTransactionCategoryCode;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private int versionNumber;
    private Collection<ExpenseEntity> expensesByTransactionCategoryCode;
    private TransactionTypeEntity transactionTypeByTransactionTypeCode;
    private TransactionCategoryEntity transactionCategoryByParentTransactionCategoryCode;
    private Collection<TransactionCategoryEntity> transactionCategoriesByTransactionCategoryCode;
    private Collection<TransactionEntity> transactonsByTransactionCategoryCode;

    @Id
    @Column(name = "TRANSACTION_CATEGORY_CODE")
    public int getTransactionCategoryCode() {
        return transactionCategoryCode;
    }

    public void setTransactionCategoryCode(int transactionCategoryCode) {
        this.transactionCategoryCode = transactionCategoryCode;
    }

    @Basic
    @Column(name = "TRANSACTION_CATEGORY_NAME")
    public String getTransactionCategoryName() {
        return transactionCategoryName;
    }

    public void setTransactionCategoryName(String transactionCategoryName) {
        this.transactionCategoryName = transactionCategoryName;
    }

    @Basic
    @Column(name = "TRANSACTION_CATEGORY_DESCRIPTION")
    public String getTransactionCategoryDescription() {
        return transactionCategoryDescription;
    }

    public void setTransactionCategoryDescription(String transactionCategoryDescription) {
        this.transactionCategoryDescription = transactionCategoryDescription;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE_CODE")
    public int getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(int transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    @Basic
    @Column(name = "PARENT_TRANSACTION_CATEGORY_CODE")
    public Integer getParentTransactionCategoryCode() {
        return parentTransactionCategoryCode;
    }

    public void setParentTransactionCategoryCode(Integer parentTransactionCategoryCode) {
        this.parentTransactionCategoryCode = parentTransactionCategoryCode;
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

        TransactionCategoryEntity that = (TransactionCategoryEntity) o;

        if (transactionCategoryCode != that.transactionCategoryCode) return false;
        if (transactionTypeCode != that.transactionTypeCode) return false;
        if (versionNumber != that.versionNumber) return false;
        if (transactionCategoryName != null ? !transactionCategoryName.equals(that.transactionCategoryName) : that.transactionCategoryName != null)
            return false;
        if (transactionCategoryDescription != null ? !transactionCategoryDescription.equals(that.transactionCategoryDescription) : that.transactionCategoryDescription != null)
            return false;
        if (parentTransactionCategoryCode != null ? !parentTransactionCategoryCode.equals(that.parentTransactionCategoryCode) : that.parentTransactionCategoryCode != null)
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
        int result = transactionCategoryCode;
        result = 31 * result + (transactionCategoryName != null ? transactionCategoryName.hashCode() : 0);
        result = 31 * result + (transactionCategoryDescription != null ? transactionCategoryDescription.hashCode() : 0);
        result = 31 * result + transactionTypeCode;
        result = 31 * result + (parentTransactionCategoryCode != null ? parentTransactionCategoryCode.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + versionNumber;
        return result;
    }

    @OneToMany(mappedBy = "transactionCategoryByTransactionCategoryCode")
    public Collection<ExpenseEntity> getExpensesByTransactionCategoryCode() {
        return expensesByTransactionCategoryCode;
    }

    public void setExpensesByTransactionCategoryCode(Collection<ExpenseEntity> expensesByTransactionCategoryCode) {
        this.expensesByTransactionCategoryCode = expensesByTransactionCategoryCode;
    }

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_TYPE_CODE", referencedColumnName = "TRANSACTION_TYPE_CODE", nullable = false)
    public TransactionTypeEntity getTransactionTypeByTransactionTypeCode() {
        return transactionTypeByTransactionTypeCode;
    }

    public void setTransactionTypeByTransactionTypeCode(TransactionTypeEntity transactionTypeByTransactionTypeCode) {
        this.transactionTypeByTransactionTypeCode = transactionTypeByTransactionTypeCode;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_TRANSACTION_CATEGORY_CODE", referencedColumnName = "TRANSACTION_CATEGORY_CODE")
    public TransactionCategoryEntity getTransactionCategoryByParentTransactionCategoryCode() {
        return transactionCategoryByParentTransactionCategoryCode;
    }

    public void setTransactionCategoryByParentTransactionCategoryCode(TransactionCategoryEntity transactionCategoryByParentTransactionCategoryCode) {
        this.transactionCategoryByParentTransactionCategoryCode = transactionCategoryByParentTransactionCategoryCode;
    }

    @OneToMany(mappedBy = "transactionCategoryByParentTransactionCategoryCode")
    public Collection<TransactionCategoryEntity> getTransactionCategoriesByTransactionCategoryCode() {
        return transactionCategoriesByTransactionCategoryCode;
    }

    public void setTransactionCategoriesByTransactionCategoryCode(Collection<TransactionCategoryEntity> transactionCategoriesByTransactionCategoryCode) {
        this.transactionCategoriesByTransactionCategoryCode = transactionCategoriesByTransactionCategoryCode;
    }

    @OneToMany(mappedBy = "transactionCategoryByExplainedTransactionCategoryCode")
    public Collection<TransactionEntity> getTransactonsByTransactionCategoryCode() {
        return transactonsByTransactionCategoryCode;
    }

    public void setTransactonsByTransactionCategoryCode(Collection<TransactionEntity> transactonsByTransactionCategoryCode) {
        this.transactonsByTransactionCategoryCode = transactonsByTransactionCategoryCode;
    }
}
