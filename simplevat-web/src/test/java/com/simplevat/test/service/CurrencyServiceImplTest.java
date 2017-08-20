package com.simplevat.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertNotNull;
import com.simplevat.entity.Currency;
import com.simplevat.service.CurrencyService;
import com.simplevat.test.common.BaseManagerTest;

public class CurrencyServiceImplTest extends BaseManagerTest{
	
	@Autowired
	CurrencyService currencyService;
	@Test
	public void test() {
		List<Currency> lists = currencyService.getCurrencies();
		assertNotNull(lists);
	}

}
