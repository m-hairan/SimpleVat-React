package com.simplevat.converter;

import com.simplevat.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by mohsin on 3/11/2017.
 */

@FacesConverter(value = "com.simplevat.util.CountryConverter")
public class CountryConverter implements Converter {

    private final static Logger LOGGER = LoggerFactory.getLogger(CountryConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Country country = new Country();
            country.setCountryCode(Integer.parseInt(value));
            return country;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Country) {
            Country country = (Country) value;
            return Integer.toString(country.getCountryCode());
        }
        return value.toString();
    }
}
