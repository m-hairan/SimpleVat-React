/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.transactioncontroller;

import com.simplevat.entity.bankaccount.TransactionCategory;

/**
 *
 * @author daynil
 */
public class TranscationCategoryHelper {

    public TransactionCategoryModel getCategory(TransactionCategory category) {
        TransactionCategoryModel model = new TransactionCategoryModel();
        model.setCreatedBy(category.getCreatedBy());
        model.setCreatedDate(category.getCreatedDate());
        model.setDefaltFlag(category.getDefaltFlag());
        model.setDeleteFlag(category.getDeleteFlag());
        model.setLastUpdateDate(category.getLastUpdateDate());
        model.setOrderSequence(category.getOrderSequence());
        model.setParentTransactionCategory(category.getParentTransactionCategory());
        model.setTransactionCategoryId(category.getTransactionCategoryId());
        model.setTransactionCategoryCode(category.getTransactionCategoryCode());
        model.setTransactionCategoryDescription(category.getTransactionCategoryDescription());
        model.setTransactionCategoryName(category.getTransactionCategoryName());
        model.setTransactionType(category.getTransactionType());
        model.setVatCategory(category.getVatCategory());
        model.setVersionNumber(category.getVersionNumber());
        return model;
    }

    public TransactionCategory getTrascationModel(TransactionCategoryModel categoryModel) {
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setCreatedBy(categoryModel.getCreatedBy());
        transactionCategory.setCreatedDate(categoryModel.getCreatedDate());
        transactionCategory.setDefaltFlag(categoryModel.getDefaltFlag());
        transactionCategory.setDeleteFlag(categoryModel.getDeleteFlag());
        transactionCategory.setLastUpdateDate(categoryModel.getLastUpdateDate());
        transactionCategory.setOrderSequence(categoryModel.getOrderSequence());
        transactionCategory.setParentTransactionCategory(categoryModel.getParentTransactionCategory());
        transactionCategory.setTransactionCategoryId(categoryModel.getTransactionCategoryId());
        transactionCategory.setTransactionCategoryCode(categoryModel.getTransactionCategoryCode());
        transactionCategory.setTransactionCategoryDescription(categoryModel.getTransactionCategoryDescription());
        transactionCategory.setTransactionCategoryName(categoryModel.getTransactionCategoryName());
        transactionCategory.setTransactionType(categoryModel.getTransactionType());
        transactionCategory.setVatCategory(categoryModel.getVatCategory());
        transactionCategory.setVersionNumber(categoryModel.getVersionNumber());
        return transactionCategory;
    }

}
