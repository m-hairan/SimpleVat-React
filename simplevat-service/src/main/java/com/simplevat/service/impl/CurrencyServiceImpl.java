package com.simplevat.service.impl;

import com.simplevat.dao.CurrencyDao;
import com.simplevat.entity.Currency;
import com.simplevat.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
@Service("currencyService")
public class CurrencyServiceImpl extends CurrencyService {

    @Autowired
    CurrencyDao currencyDao;

    @Override
    public List<Currency> getCurrencies() {
        return getDao().getCurrencies();
    }

    @Override
    public Currency getCurrency(final int currencyCode) {
        return getDao().getCurrency(currencyCode);
    }

    @Override
    public Currency getDefaultCurrency() {
        return getDao().getDefaultCurrency();
    }

	@Override
	public CurrencyDao getDao() {
		return currencyDao;
	}
}
