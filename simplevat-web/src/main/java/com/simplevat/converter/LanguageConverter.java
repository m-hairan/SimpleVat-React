package com.simplevat.converter;

import com.simplevat.entity.Language;
import com.simplevat.service.LanguageService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mohsin on 3/11/2017.
 */
@Service
public class LanguageConverter implements Converter {

    @Autowired
    private LanguageService languageService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Language language = languageService.getLanguage(Integer.parseInt(value));
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
