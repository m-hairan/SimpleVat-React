/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.transactioncategory.controller;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.web.transactioncategory.model.TransactionCategoryModel;

/**
 *
 * @author daynil
 */
public class TranscationCategoryHelper {
    
    
   public TransactionCategoryModel getCategory(TransactionCategory  category)
   {
   TransactionCategoryModel model=new TransactionCategoryModel();
   model.setCreatedBy(category.getCreatedBy());
   model.setCreatedDate(category.getCreatedDate());
   model.setDefaltFlag(category.getDefaltFlag());
   model.setDeleteFlag(category.getDeleteFlag());
   model.setLastUpdateDate(category.getLastUpdateDate());
   model.setOrderSequence(category.getOrderSequence());
   model.setParentTransactionCategory(category.getParentTransactionCategory());
   model.setTransactionCategoryCode(category.getTransactionCategoryCode());
   model.setTransactionCategoryDescription(category.getTransactionCategoryDescription());
   model.setTransactionCategoryName(category.getTransactionCategoryName());
   model.setTransactionType(category.getTransactionType());
   model.setVersionNumber(category.getVersionNumber());
   return model;
   }
 

 public TransactionCategory getTrascationModel(TransactionCategoryModel category)
 {
   TransactionCategory model=new TransactionCategory();
   model.setCreatedBy(category.getCreatedBy());
   model.setCreatedDate(category.getCreatedDate());
   model.setDefaltFlag(category.getDefaltFlag());
   model.setDeleteFlag(category.getDeleteFlag());
   model.setLastUpdateDate(category.getLastUpdateDate());
   model.setOrderSequence(category.getOrderSequence());
   model.setParentTransactionCategory(category.getParentTransactionCategory());
   model.setTransactionCategoryCode(category.getTransactionCategoryCode());
   model.setTransactionCategoryDescription(category.getTransactionCategoryDescription());
   model.setTransactionCategoryName(category.getTransactionCategoryName());
   model.setTransactionType(category.getTransactionType());
   model.setVersionNumber(category.getVersionNumber());
   return model;
 }
    
}
