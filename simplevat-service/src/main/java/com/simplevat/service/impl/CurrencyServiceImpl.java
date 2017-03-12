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
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CurrencyDao CurrencyDao;

    @Override
    public List<Currency> getCurrencies() {
        return CurrencyDao.getCurrencies();
    }
}
