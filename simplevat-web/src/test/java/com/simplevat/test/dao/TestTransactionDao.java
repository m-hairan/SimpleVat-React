package com.simplevat.test.dao;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.bankaccount.TransactionDao;
import com.simplevat.test.common.BaseManagerTest;

public class TestTransactionDao extends BaseManagerTest{
	
	@Autowired
	TransactionDao transactionDao;
	
	
	@Test
	public void getRecords(){
		transactionDao.getCashInData();
	}
	

	
	public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
	
	
	

}
