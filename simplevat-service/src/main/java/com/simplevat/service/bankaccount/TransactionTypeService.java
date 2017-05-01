package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.bankaccount.TransactionType;

public interface TransactionTypeService {
	
	public List<TransactionType> getTransactionTypesByCriteria(TransactionTypeCriteria transactionTypeCriteria) throws Exception;

	public TransactionType updateOrCreateTransaction(TransactionType transactionType);
	
	public TransactionType getTransactionType(Integer id);

}
