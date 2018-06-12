package com.simplevat.service;

import com.simplevat.entity.Currency;
import com.simplevat.entity.CurrencyConversion;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public abstract class CurrencyService extends SimpleVatService<Integer, Currency> {

	public abstract List<Currency> getCurrencies();
    
	public abstract Currency getCurrency(final int currencyCode);

	public abstract Currency getDefaultCurrency();
        
        public abstract CurrencyConversion getCurrencyRateFromCurrencyConversion(final int currencyCode);
        
        public abstract String getCountryCodeAsString(String CountryCode);
        
        public abstract List<Currency> getCurrencyList(Currency currency);
        
        public abstract List<String> getCountryCodeString();
}
