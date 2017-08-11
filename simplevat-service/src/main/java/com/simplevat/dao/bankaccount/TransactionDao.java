package com.simplevat.dao.bankaccount;

import java.util.Date;
import java.util.List;

import com.simplevat.dao.Dao;
import com.simplevat.entity.bankaccount.Transaction;

public interface TransactionDao extends Dao<Integer, Transaction> {

	 Transaction updateOrCreateTransaction(Transaction transaction);
	 
	 public List<Object[]> getCashInData(Date startDate, Date endDate);
	 
	 public List<Object[]> getCashOutData(Date startDate, Date endDate);
	    
}
