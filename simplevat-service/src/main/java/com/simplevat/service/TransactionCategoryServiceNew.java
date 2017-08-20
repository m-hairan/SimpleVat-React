package com.simplevat.service;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.entity.bankaccount.TransactionCategory;

public abstract class TransactionCategoryServiceNew extends SimpleVatService<Integer, TransactionCategory> {
	
	public abstract  List<TransactionCategory> findAllTransactionCategory();
	
	public abstract  List<TransactionCategory> getCategoriesByComplexCriteria(TransactionCategoryCriteria criteria);
	
	public abstract  TransactionCategory getDefaultTransactionCategory();

}
