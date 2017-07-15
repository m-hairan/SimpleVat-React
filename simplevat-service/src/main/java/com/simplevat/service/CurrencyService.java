package com.simplevat.service;

import com.simplevat.entity.Currency;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public interface CurrencyService extends SimpleVatService<Integer, Currency> {

    List<Currency> getCurrencies();
    
    Currency getCurrency(final int currencyCode);

    Currency getDefaultCurrency();
}
