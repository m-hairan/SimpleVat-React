package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;

public interface TransactionDao {

	 List<Transaction> getTransactionsByCriteria(TransactionCriteria transactionCriteria) throws Exception;

	 Transaction updateOrCreateTransaction(Transaction transaction);
	    
}
