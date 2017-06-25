package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.bankaccount.TransactionType;

public interface TransactionTypeDao {
	
	public List<TransactionType> getTransactionTypesByCriteria(TransactionTypeCriteria transactionTypeCriteria) throws Exception;

	public TransactionType updateOrCreateTransaction(TransactionType transactionType);
	
	public TransactionType getTransactionType(Integer id);
	
	public TransactionType getDefaultTransactionType();
	
	public List<TransactionType> findAll();

}
