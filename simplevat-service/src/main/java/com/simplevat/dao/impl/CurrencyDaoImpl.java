package com.simplevat.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.dao.CurrencyDao;
import com.simplevat.entity.Currency;
import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.CurrencyConversion;
import javax.persistence.TypedQuery;

/**
 * Created by mohsin on 3/11/2017.
 */
@Repository
public class CurrencyDaoImpl extends AbstractDao<Integer, Currency> implements CurrencyDao {

    @Override
    public List<Currency> getCurrencies() {
        List<Currency> languages = this.executeNamedQuery("allCurrencies");
        return languages;
    }

    @Override
    public Currency getDefaultCurrency() {
        List<Currency> currencies = getCurrencies();
        if (CollectionUtils.isNotEmpty(currencies)) {
            return currencies.get(0);
        }
        return null;

    }

    public Currency getCurrency(final int currencyCode) {
        return this.findByPK(currencyCode);
    }

    @Override
    public CurrencyConversion getCurrencyRateFromCurrencyConversion(int currencyCode) {
        TypedQuery<CurrencyConversion> query = getEntityManager().createQuery("select c from CurrencyConversion c where c.currencyCode =:currencyCode", CurrencyConversion.class);
        query.setParameter("currencyCode", currencyCode);
        List<CurrencyConversion> currencyConversionList = query.getResultList();
        if (currencyConversionList != null && !currencyConversionList.isEmpty()) {
            return currencyConversionList.get(0);
        }
        return null;
    }
}
