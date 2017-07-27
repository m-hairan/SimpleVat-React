package com.simplevat.web.converter;

import com.simplevat.entity.Country;
import com.simplevat.service.CountryService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mohsin on 3/11/2017.
 */
@Service
public class CountryConverter implements Converter {

    @Autowired
    private CountryService countryService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CountryConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Country country = countryService.getCountry(Integer.parseInt(value));
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
