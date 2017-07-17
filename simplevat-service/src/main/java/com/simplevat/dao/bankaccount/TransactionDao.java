package com.simplevat.dao.bankaccount;

import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.Transaction;

public interface TransactionDao extends Dao<Integer, Transaction> {

	 Transaction updateOrCreateTransaction(Transaction transaction);
	 
	 public List<Object[]> getCashInData();
	 
	 public List<Object[]> getCashOutData();
	    
}
