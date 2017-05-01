package com.simplevat.service.bankaccount;

import java.util.List;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;

public interface TransactionService {
	
	List<Transaction> getTransactionsByCriteria(
			TransactionCriteria transactionCriteria) throws Exception;

	Transaction updateOrCreateTransaction(Transaction transaction);

}
