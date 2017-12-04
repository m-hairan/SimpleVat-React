package com.simplevat.web.converter;

import com.simplevat.entity.Currency;
import com.simplevat.service.CurrencyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mohsin on 3/11/2017.
 */
@Service
public class CurrencyConverter implements Converter {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Currency currency = currencyService.getCurrency(Integer.parseInt(value));
                return currency;
            } catch (Exception e) {
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Currency) {
            Currency currency = (Currency) value;
            return currency.getCurrencyCode().toString();
        }
        return value.toString();
    }
}
