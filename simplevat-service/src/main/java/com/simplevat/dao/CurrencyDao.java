package com.simplevat.dao;

import com.simplevat.entity.Currency;

import java.util.List;

/**
 * Created by mohsin on 3/11/2017.
 */
public interface CurrencyDao {

    List<Currency> getCurrencies();
    
    Currency getCurrency(final int currencyCode);
}
