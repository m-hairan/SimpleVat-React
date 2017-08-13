package com.simplevat.service.impl.bankaccount;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.criteria.bankaccount.TransactionFilter;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.util.ChartUtil;

@Service("transactionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	ChartUtil util;

	@Autowired
	private TransactionDao transactionDao;

	@Override
	public List<Transaction> getTransactionsByCriteria(TransactionCriteria transactionCriteria) throws Exception {
		TransactionFilter filter = new TransactionFilter(transactionCriteria);
		return transactionDao.filter(filter);
	}

	@Override
	public Transaction updateOrCreateTransaction(Transaction transaction) {
		return transactionDao.updateOrCreateTransaction(transaction);
	}

	@Override
	public Map<Object, Number> getCashOutData() {
		List<Object[]> rows = transactionDao.getCashOutData(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}

	@Override
	public Map<Object, Number> getCashInData() {
		List<Object[]> rows = transactionDao.getCashInData(util.getStartDate(Calendar.YEAR,-1).getTime(),util.getEndDate().getTime());
		return util.getCashMap(rows);
	}


	public int getMaxTransactionValue(Map<Object, Number> cashInMap, Map<Object, Number> cashOutMap) {
		int cashInMax = util.getMaxValue(cashInMap);
		int cashOutMax = util.getMaxValue(cashOutMap);
		if(cashInMax >= cashOutMax) {
			return cashInMax;
		}else {
			return cashOutMax;
		}
	}

	@Override
	public TransactionDao getDao() {
		return this.transactionDao;
	}
	
}
