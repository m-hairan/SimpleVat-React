package com.simplevat.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.simplevat.dao.CurrencyDao;
import com.simplevat.entity.Currency;
import com.simplevat.dao.AbstractDao;
import com.simplevat.entity.CurrencyConversion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;

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
        Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date dateWithoutTime = cal.getTime();
        TypedQuery<CurrencyConversion> query = getEntityManager().createQuery("select c from CurrencyConversion c where c.currencyCodeConvertedTo =:currencyCode AND c.createdDate=:createdDate", CurrencyConversion.class);
        query.setParameter("currencyCode", currencyCode);
        query.setParameter("createdDate",LocalDateTime.ofInstant(dateWithoutTime.toInstant(), ZoneId.systemDefault()));
        List<CurrencyConversion> currencyConversionList = query.getResultList();
        if (currencyConversionList != null && !currencyConversionList.isEmpty()) {
            return currencyConversionList.get(0);
        }
        return null;
    }

    @Override
    public String getCountryCodeAsString(String CountryCode) {
        Query query = getEntityManager().createQuery("select c.currencyIsoCode from Currency c where c.currencyIsoCode !=:currencyCode");
        query.setParameter("currencyCode", CountryCode);
        List<String> currency = query.getResultList();
        String name = StringUtils.join(currency, ',');
        System.out.println("currency===" + name);
// query.setParameter("currencyCode", currencyCode);

        return name;
    }
    
     public List<Currency> getCurrencyList(Currency currency) {
        Query query = getEntityManager().createQuery("select c from Currency c where c.currencyIsoCode !=:currencyCode");
        query.setParameter("currencyCode", currency.getCurrencyIsoCode());
        List<Currency> currencyList = query.getResultList();
       
// query.setParameter("currencyCode", currencyCode);

        return currencyList;
    }
    
    @Override
    public List<String> getCountryCodeString() {
        Query query = getEntityManager().createQuery("select c.currencyIsoCode from Currency c");
        List<String> currency = query.getResultList();
        return currency;
    }
}
