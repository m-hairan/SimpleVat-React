package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.dao.AbstractDao;

@Repository
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {

	@Override
	public Transaction updateOrCreateTransaction(Transaction transaction) {
		return this.update(transaction);
	}

	@Override
	public List<Object[]> getCashInData() {
		List<Object[]> cashInData = new ArrayList<>(0);
		try{
			String queryString = "select "
					+ "sum(transactionAmount) as total, Month(transactionDate) as month "
					+ "from Transaction "
					+ "where debitCreditFlag = 'd' "
					+ "group by Month(transactionDate)";
		Query query = getEntityManager().createQuery(queryString);
		cashInData  = query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cashInData;
	}
	
	@Override
	public List<Object[]> getCashOutData() {
		List<Object[]> cashOutData = new ArrayList<>(0);
		try{
			String queryString = "select "
					+ "sum(transactionAmount) as total, Month(transactionDate) as month "
					+ "from Transaction "
					+ "where debitCreditFlag = 'c' "
					+ "group by Month(transactionDate)";
		Query query = getEntityManager().createQuery(queryString);
		cashOutData  = query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cashOutData;
	}

}
