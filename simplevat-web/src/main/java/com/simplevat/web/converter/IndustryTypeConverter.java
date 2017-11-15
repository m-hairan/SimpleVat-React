/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.IndustryType;
import com.simplevat.service.IndustryTypeService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author uday
 */
@Service
public class IndustryTypeConverter implements Converter {

    @Autowired
    private IndustryTypeService industryTypeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            IndustryType industryType = industryTypeService.findByPK(Integer.parseInt(string));
            return industryType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof IndustryType) {
            IndustryType industryType = (IndustryType) o;
            return Integer.toString(industryType.getId());
        }
        return null;
    }

}
