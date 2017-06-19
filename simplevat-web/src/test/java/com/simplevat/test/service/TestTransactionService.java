package com.simplevat.test.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.test.common.BaseManagerTest;

/**
 * @author Ankit
 *
 */
public class TestTransactionService extends BaseManagerTest {
	
	@Autowired
	public TransactionService transactionService;

	@Test
	public void getcheckInData() {
		Map<Object, Number> map = transactionService.getCashInData();
		
		for (Object obj : map.keySet()) {
			Number n = map.get(obj);
			System.out.println((String)obj +"-------------------"+n);
		}
		System.out.println("Check in size ::: "+map.size());
	}

	
	@Test
	public void getcheckOutData() {
		Map<Object, Number> map = transactionService.getCashOutData();
		System.out.println("Check Out size :: "+map.size());
		for (Object obj : map.keySet()) {
			Number n = map.get(obj);
			System.out.println((String)obj +"-------------------"+n);
			
		}
	}
	@Test
	public void getMaxValue(){
		int d = transactionService.getMaxTransactionValue();
		System.out.println("Max Value is ::::::::::::::::::::::::  "+d);
	}

}
