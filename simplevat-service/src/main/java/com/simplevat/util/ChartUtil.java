package com.simplevat.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.simplevat.service.impl.bankaccount.ChartData;

@Component
public class ChartUtil {
	
	public Map<Object, Number> getCashMap(List<Object[]> rows) {
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
			String[] transactionDate = ((String) row[1]).split("-");
			ChartData chartData = new ChartData(Integer.parseInt(transactionDate[0])-1, Integer.parseInt(transactionDate[1]),(Number) row[0] );
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
	
	public Calendar getStartDate() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		prevYear.set(Calendar.DAY_OF_MONTH, 1);
		return prevYear;
	}

	public Calendar getEndDate() {
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
	
	public int getMaxValue(Map<Object, Number> data) {
		List<Number> list = new ArrayList<Number>();
		list.addAll(data.values());
		Number max = 0;
		for (int i = 0; i < list.size(); i++) {
			Number number = list.get(i);
			if (number.doubleValue()> max.doubleValue()) {
				max = number;
			}
		}
		int maxValue = 0;
		if(max.doubleValue() < 10) {
			maxValue = max.intValue()*2;
		}else {
			maxValue = max.intValue() + max.intValue()*1/5;
		}
		return maxValue;
		
	}

}
