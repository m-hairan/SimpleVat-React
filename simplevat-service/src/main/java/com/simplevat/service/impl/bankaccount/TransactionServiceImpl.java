package com.simplevat.service.impl.bankaccount;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.bankaccount.TransactionCriteria;
import com.simplevat.criteria.bankaccount.TransactionFilter;
import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.utils.DateUtils;

@Service("transactionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {

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
		List<Object[]> rows = transactionDao.getCashOutData();
		// Map<Object, Number> cashOutMap = new LinkedHashMap<>(0);
		Map<Object, Number> cashOutMap = prepairCashFlowChartObj();
		for (Object[] row : rows) {
			int i = (int) row[1];
			String month = DateUtils.getMonthForInt(i - 1);
			Number amt = (Number) row[0];
			cashOutMap.put(month, amt);
		}
		return cashOutMap;
	}

	/* (non-Javadoc)
	 * @see com.simplevat.service.bankaccount.TransactionService#getCashInData()
	 */
	@Override
	public Map<Object, Number> getCashInData() {
		List<Object[]> rows = transactionDao.getCashInData();
		// Map<Object, Number> cashInMap = new LinkedHashMap<>(0);
		Map<Object, Number> cashInMap = prepairCashFlowChartObj();
		for (Object[] row : rows) {
			int i = (int) row[1];
			String month = DateUtils.getMonthForInt(i - 1);
			Number amt = (Number) row[0];
			cashInMap.put(month, amt);
		}
		return cashInMap;
	}

	/**
	 * @return 
	 */
	public Map<Object, Number> prepairCashFlowChartObj() {
		Map<Object, Number> cashInMap = new LinkedHashMap<>(0);
		for (int i = 0; i < 12; i++) {
			String key = DateUtils.getMonthForInt(i);
			cashInMap.put(key, 0);
		}
		return cashInMap;
	}

	/* (non-Javadoc)
	 * @see com.simplevat.service.bankaccount.TransactionService#getMaxTransactionValue()
	 */
	public int getMaxTransactionValue() {
		Map<Object, Number> cashInMap = getCashInData();
		Map<Object, Number> cashOutMap = getCashOutData();
		List<Number> list = new ArrayList<Number>();
		list.addAll(cashOutMap.values());
		list.addAll(cashInMap.values());
		Number max = 0;
		for (int i = 0; i < list.size(); i++) {
			Number number = list.get(i);
			if (number.intValue() > max.intValue()) {
				max = number;
			}
		}
		int y = max.intValue() + 50;
		int value = y - ((y % 50));
		return value;
	}

	@Override
	public TransactionDao getDao() {
		return this.transactionDao;
	}
}
