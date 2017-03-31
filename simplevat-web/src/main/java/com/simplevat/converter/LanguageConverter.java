package com.simplevat.converter;

import com.simplevat.entity.Language;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by mohsin on 3/11/2017.
 */

@FacesConverter(value = "com.simplevat.util.LanguageConverter")
public class LanguageConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Language language = new Language();
            language.setLanguageCode(Integer.parseInt(value));
            return language;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Language) {
            Language language = (Language) value;
            return Integer.toString(language.getLanguageCode());
        }
        return value.toString();
    }
}