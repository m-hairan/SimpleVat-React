package com.simplevat.util;

import com.simplevat.entity.Title;
import org.apache.log4j.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by mohsin on 3/12/2017.
 */
@FacesConverter( value = "com.simplevat.util.TitleConverter")
public class TitleConverter implements Converter {

    final static Logger logger = Logger.getLogger(TitleConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.isEmpty())
        {
            Title title = new Title();
            title.setTitleCode(Integer.parseInt(value));
            return title;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
        {
            return null;

        } else if (value instanceof Title) {
            Title title = (Title) value;
            return Integer.toString(title.getTitleCode());
        }
        return value.toString();
    }
}
