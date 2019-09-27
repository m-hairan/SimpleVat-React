package com.simplevat.service;

import com.simplevat.entity.Currency;
import com.simplevat.entity.CurrencyConversion;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public abstract class CurrencyExchangeService extends SimpleVatService<Integer, CurrencyConversion> {
	public abstract void saveExchangeCurrencies(Currency baseCurrency,List<Currency> convertCurrenies);
}
