package com.simplevat.service.impl.bankaccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
		List<Object[]> rows = transactionDao.getCashOutData(getStartDate().getTime(),getEndDate().getTime());
		return getCashMap(rows);
	}

	@Override
	public Map<Object, Number> getCashInData() {
		List<Object[]> rows = transactionDao.getCashInData(getStartDate().getTime(),getEndDate().getTime());
		return getCashMap(rows);
	}

	private Map<Object, Number> getCashMap(List<Object[]> rows) {
		Map<Object, Number> cashMap = new LinkedHashMap<>(0);
		if(rows == null || rows.size() == 0) {
			return cashMap;
		}
		List<ChartData> chartDatas = convert(rows);
		Collections.sort(chartDatas);
		
		chartDatas = populateForAlltheMonths(chartDatas);
		
		for (ChartData chartData : chartDatas) {
			cashMap.put(chartData.getKey(), chartData.getAmount());
		}
		return cashMap;
	}

	private List<ChartData> convert(List<Object[]> rows) {
		List<ChartData> chartDatas = new ArrayList<ChartData>();
		for (Object[] row : rows) {
			
			String[] transactionDate = ((String) row[1]).split(" ");
			ChartData chartData = new ChartData(Integer.parseInt(transactionDate[0]), Integer.parseInt(transactionDate[1]),(Number) row[0] );
			chartDatas.add(chartData);
		}
		
		return chartDatas;
	}
	
	private List<ChartData> populateForAlltheMonths(List<ChartData> chartDatas) {
		List<ChartData> emptyChartDatas = getEmptyChartData();
		if(chartDatas == null || chartDatas.size() ==0) {
			return emptyChartDatas;
		}
		for(ChartData emptyChartData :  emptyChartDatas) {
			for(ChartData chartData : chartDatas) {
				if(emptyChartData.equals(chartData)) {
					emptyChartData.setAmount(chartData.getAmount());
					break;
				}
			}
		}
		return emptyChartDatas;
		
	}
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
	
	private Calendar getStartDate() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		prevYear.set(Calendar.DAY_OF_MONTH, 1);
		return prevYear;
	}

	private Calendar getEndDate() {
		Calendar preMonth = Calendar.getInstance();
		//preMonth.add(Calendar.MONTH, -1);
		preMonth.set(Calendar.DAY_OF_MONTH, 1);
		preMonth.add(Calendar.DAY_OF_MONTH, -1);
		return preMonth;
	}
	
	private List<ChartData> getEmptyChartData() {
		List<ChartData> emptyChartDatas = new ArrayList<ChartData>();
		for(int i=0;i<12;i++) {
			Calendar calendar = getStartDate();
			calendar.add(Calendar.MONTH, i);
			ChartData chartData = new ChartData(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),0);
			emptyChartDatas.add(chartData);
		}
		return emptyChartDatas;
	}
	
}
