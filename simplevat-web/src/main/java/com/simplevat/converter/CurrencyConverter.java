package com.simplevat.converter;

import com.simplevat.entity.Currency;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by mohsin on 3/11/2017.
 */

@FacesConverter(value = "com.simplevat.util.CurrencyConverter")
public class CurrencyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Currency currency = new Currency();
            currency.setCurrencyCode(Integer.parseInt(value));
            return currency;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Currency) {
            Currency currency = (Currency) value;
            return Integer.toString(currency.getCurrencyCode());
        }
        return value.toString();
    }
}