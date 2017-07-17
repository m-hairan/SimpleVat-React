package com.simplevat.service.bankaccount;

import java.util.List;
import java.util.Map;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.SimpleVatService;

public interface TransactionService extends SimpleVatService<Integer, Transaction> {
	
	public List<Transaction> getTransactionsByCriteria(
			TransactionCriteria transactionCriteria) throws Exception;

	public Transaction updateOrCreateTransaction(Transaction transaction);
	
	public Map<Object, Number> getCashOutData();
	
	public Map<Object, Number> getCashInData();
	
	public int getMaxTransactionValue();

}
