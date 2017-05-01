package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionCategoryCriteria;
import com.simplevat.entity.bankaccount.TransactionCategory;

public interface TransactionCategoryDao {
	
	public List<TransactionCategory> getTransactionCategoriesByCriteria(TransactionCategoryCriteria transactionCategoryCriteria) throws Exception;

	public TransactionCategory updateOrCreateTransaction(TransactionCategory transactionCategory);
	
	public TransactionCategory getTransactionCategory(Integer id);

}
