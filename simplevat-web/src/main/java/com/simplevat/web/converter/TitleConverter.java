package com.simplevat.web.converter;

import com.simplevat.entity.Title;
import com.simplevat.service.TitleService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mohsin on 3/12/2017.
 */
@Service
public class TitleConverter implements Converter {

    @Autowired
    private TitleService titleService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !value.isEmpty()) {
            try {
                Title title = (Title) titleService.findByPK(Integer.parseInt(value));
                return title;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;

        } else if (value instanceof Title) {
            Title title = (Title) value;
            return Integer.toString(title.getTitleCode());
        }
        return value.toString();
    }
}
