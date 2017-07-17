package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionTypeCriteria;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.SimpleVatService;

public interface TransactionTypeService extends SimpleVatService<Integer, TransactionType> {
	
	public List<TransactionType> getTransactionTypesByCriteria(TransactionTypeCriteria transactionTypeCriteria) throws Exception;

	public TransactionType updateOrCreateTransaction(TransactionType transactionType);
	
	public TransactionType getTransactionType(Integer id);
	
	public TransactionType getDefaultTransactionType();
	
	public List<TransactionType> findAll();

}
