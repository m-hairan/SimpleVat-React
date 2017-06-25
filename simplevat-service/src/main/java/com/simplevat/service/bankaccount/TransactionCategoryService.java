package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.entity.bankaccount.TransactionCategory;

public interface TransactionCategoryService {
	
	public List<TransactionCategory> getTransactionCategoriesByCriteria(TransactionCategoryCriteria transactionCategoryCriteria) throws Exception;

	public TransactionCategory updateOrCreateTransaction(TransactionCategory transactionCategory);
	
	public TransactionCategory getDefaultTransactionCategory();
	
	public TransactionCategory getTransactionCategory(Integer id);

}
