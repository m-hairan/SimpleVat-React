package com.simplevat.dao.bankaccount;

import java.util.List;
import java.util.Map;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;

public interface TransactionDao {

	 List<Transaction> getTransactionsByCriteria(TransactionCriteria transactionCriteria) throws Exception;

	 Transaction updateOrCreateTransaction(Transaction transaction);
	 
	 public List<Object[]> getCashInData();
	 
	 public List<Object[]> getCashOutData();
	    
}
