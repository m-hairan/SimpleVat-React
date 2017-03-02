package com.simplevat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "EXPENSE", schema = "simplevat", catalog = "")
public class ExpenseEntity {
    private int expenseId;
    private BigDecimal expenseAmount;
    private Date expenseDate;
    private String expenseDescription;
    private String receiptNumber;
    private Integer claimantId;
    private Integer transactionTypeCode;
    private Integer transactionCategoryCode;
    private Integer currencyCode;
    private Integer projectId;
    private String receiptAttachmentPath;
    private String receiptAttachmentDescription;
    private Integer createdBy;
    private Date createdDate;
    private Date lastUpdatedBy;
    private Date lastUpdateDate;
    private String deleteFlag;
    private UserEntity userByClaimantId;
    private TransactionTypeEntity transactionTypeByTransactionTypeCode;
    private TransactionCategoryEntity transactionCategoryByTransactionCategoryCode;
    private CurrencyEntity currencyByCurrencyCode;
    private ProjectEntity projectByProjectId;

    @Id
    @Column(name = "EXPENSE_ID")
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    @Basic
    @Column(name = "EXPENSE_AMOUNT")
    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Basic
    @Column(name = "EXPENSE_DATE")
    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Basic
    @Column(name = "EXPENSE_DESCRIPTION")
    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
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
    @Column(name = "CLAIMANT_ID")
    public Integer getClaimantId() {
        return claimantId;
    }

    public void setClaimantId(Integer claimantId) {
        this.claimantId = claimantId;
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
    @Column(name = "TRANSACTION_CATEGORY_CODE")
    public Integer getTransactionCategoryCode() {
        return transactionCategoryCode;
    }

    public void setTransactionCategoryCode(Integer transactionCategoryCode) {
        this.transactionCategoryCode = transactionCategoryCode;
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
    @Column(name = "PROJECT_ID")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "RECEIPT_ATTACHMENT_PATH")
    public String getReceiptAttachmentPath() {
        return receiptAttachmentPath;
    }

    public void setReceiptAttachmentPath(String receiptAttachmentPath) {
        this.receiptAttachmentPath = receiptAttachmentPath;
    }

    @Basic
    @Column(name = "RECEIPT_ATTACHMENT_DESCRIPTION")
    public String getReceiptAttachmentDescription() {
        return receiptAttachmentDescription;
    }

    public void setReceiptAttachmentDescription(String receiptAttachmentDescription) {
        this.receiptAttachmentDescription = receiptAttachmentDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseEntity that = (ExpenseEntity) o;

        if (expenseId != that.expenseId) return false;
        if (expenseAmount != null ? !expenseAmount.equals(that.expenseAmount) : that.expenseAmount != null)
            return false;
        if (expenseDate != null ? !expenseDate.equals(that.expenseDate) : that.expenseDate != null) return false;
        if (expenseDescription != null ? !expenseDescription.equals(that.expenseDescription) : that.expenseDescription != null)
            return false;
        if (receiptNumber != null ? !receiptNumber.equals(that.receiptNumber) : that.receiptNumber != null)
            return false;
        if (claimantId != null ? !claimantId.equals(that.claimantId) : that.claimantId != null) return false;
        if (transactionTypeCode != null ? !transactionTypeCode.equals(that.transactionTypeCode) : that.transactionTypeCode != null)
            return false;
        if (transactionCategoryCode != null ? !transactionCategoryCode.equals(that.transactionCategoryCode) : that.transactionCategoryCode != null)
            return false;
        if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (receiptAttachmentPath != null ? !receiptAttachmentPath.equals(that.receiptAttachmentPath) : that.receiptAttachmentPath != null)
            return false;
        if (receiptAttachmentDescription != null ? !receiptAttachmentDescription.equals(that.receiptAttachmentDescription) : that.receiptAttachmentDescription != null)
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
        int result = expenseId;
        result = 31 * result + (expenseAmount != null ? expenseAmount.hashCode() : 0);
        result = 31 * result + (expenseDate != null ? expenseDate.hashCode() : 0);
        result = 31 * result + (expenseDescription != null ? expenseDescription.hashCode() : 0);
        result = 31 * result + (receiptNumber != null ? receiptNumber.hashCode() : 0);
        result = 31 * result + (claimantId != null ? claimantId.hashCode() : 0);
        result = 31 * result + (transactionTypeCode != null ? transactionTypeCode.hashCode() : 0);
        result = 31 * result + (transactionCategoryCode != null ? transactionCategoryCode.hashCode() : 0);
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (receiptAttachmentPath != null ? receiptAttachmentPath.hashCode() : 0);
        result = 31 * result + (receiptAttachmentDescription != null ? receiptAttachmentDescription.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CLAIMANT_ID", referencedColumnName = "USER_EMAIL_ID")
    public UserEntity getUserByClaimantId() {
        return userByClaimantId;
    }

    public void setUserByClaimantId(UserEntity userByClaimantId) {
        this.userByClaimantId = userByClaimantId;
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
    @JoinColumn(name = "TRANSACTION_CATEGORY_CODE", referencedColumnName = "TRANSACTION_CATEGORY_CODE")
    public TransactionCategoryEntity getTransactionCategoryByTransactionCategoryCode() {
        return transactionCategoryByTransactionCategoryCode;
    }

    public void setTransactionCategoryByTransactionCategoryCode(TransactionCategoryEntity transactionCategoryByTransactionCategoryCode) {
        this.transactionCategoryByTransactionCategoryCode = transactionCategoryByTransactionCategoryCode;
    }

    @ManyToOne
    @JoinColumn(name = "CURRENCY_CODE", referencedColumnName = "CURRENCY_CODE")
    public CurrencyEntity getCurrencyByCurrencyCode() {
        return currencyByCurrencyCode;
    }

    public void setCurrencyByCurrencyCode(CurrencyEntity currencyByCurrencyCode) {
        this.currencyByCurrencyCode = currencyByCurrencyCode;
    }

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    public ProjectEntity getProjectByProjectId() {
        return projectByProjectId;
    }

    public void setProjectByProjectId(ProjectEntity projectByProjectId) {
        this.projectByProjectId = projectByProjectId;
    }
}
