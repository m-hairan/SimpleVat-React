package com.simplevat.service;

import com.simplevat.entity.Currency;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public abstract class CurrencyService extends SimpleVatService<Integer, Currency> {

	public abstract List<Currency> getCurrencies();
    
	public abstract Currency getCurrency(final int currencyCode);

	public abstract Currency getDefaultCurrency();
}
