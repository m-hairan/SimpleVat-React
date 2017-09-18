/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.transactioncategory.model;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import java.time.LocalDateTime;

/**
 *
 * @author daynil
 */
public class TransactionCategoryModel {

    private Integer transactionCategoryCode;
    private String transactionCategoryName;
    private String transactionCategoryDescription;
    private TransactionType transactionType;
    private TransactionCategory parentTransactionCategory;
    private Character defaltFlag;
    private Integer orderSequence;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private Integer lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private Boolean deleteFlag = Boolean.FALSE;
    private Integer versionNumber;

    public Integer getTransactionCategoryCode() {
        return transactionCategoryCode;
    }

    public void setTransactionCategoryCode(Integer transactionCategoryCode) {
        this.transactionCategoryCode = transactionCategoryCode;
    }

    public String getTransactionCategoryName() {
        return transactionCategoryName;
    }

    public void setTransactionCategoryName(String transactionCategoryName) {
        this.transactionCategoryName = transactionCategoryName;
    }

    public String getTransactionCategoryDescription() {
        return transactionCategoryDescription;
    }

    public void setTransactionCategoryDescription(String transactionCategoryDescription) {
        this.transactionCategoryDescription = transactionCategoryDescription;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    
    
    public TransactionCategory getParentTransactionCategory() {
        return parentTransactionCategory;
    }

    
    public void setParentTransactionCategory(TransactionCategory parentTransactionCategory) {
        this.parentTransactionCategory = parentTransactionCategory;
    }

    public Character getDefaltFlag() {
        return defaltFlag;
    }

    public void setDefaltFlag(Character defaltFlag) {
        this.defaltFlag = defaltFlag;
    }

    public Integer getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(Integer orderSequence) {
        this.orderSequence = orderSequence;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

}
