package com.simplevat.dao.impl.bankaccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.Transaction;

@Repository
public class TransactionDaoImpl extends AbstractDao<Integer, Transaction> implements TransactionDao {

	@Override
	public Transaction updateOrCreateTransaction(Transaction transaction) {
		return this.update(transaction);
	}

	@Override
	public List<Object[]> getCashInData(Date startDate, Date endDate) {
		List<Object[]> cashInData = new ArrayList<>(0);
		try {
			String queryString = "select "
					+ "sum(transactionAmount) as total, CONCAT(MONTH(transactionDate),'-' , Year(transactionDate)) as month "
					+ "from Transaction "
					+ "where debitCreditFlag = 'd' and transactionDate BETWEEN :startDate AND :endDate "
					+ "group by CONCAT(MONTH(transactionDate),'-' , Year(transactionDate))";

			Query query = getEntityManager().createQuery(queryString)
					.setParameter("startDate", startDate, TemporalType.DATE)
					.setParameter("endDate", endDate, TemporalType.DATE);
			cashInData = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cashInData;
	}

	@Override
	public List<Object[]> getCashOutData(Date startDate, Date endDate) {
		List<Object[]> cashOutData = new ArrayList<>(0);
		try {
			String queryString = "select "
					+ "sum(transactionAmount) as total, CONCAT(MONTH(transactionDate),'-' , Year(transactionDate)) as month "
					+ "from Transaction "
					+ "where debitCreditFlag = 'c' and transactionDate BETWEEN :startDate AND :endDate "
					+ "group by CONCAT(MONTH(transactionDate),'-' , Year(transactionDate))";
			Query query = getEntityManager().createQuery(queryString)
					.setParameter("startDate", startDate, TemporalType.DATE)
					.setParameter("endDate", endDate, TemporalType.DATE);
			cashOutData = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cashOutData;
	}


}
