/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.CompanyType;
import com.simplevat.service.CompanyTypeService;
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
public class CompanyTypeConverter implements Converter {

    @Autowired
    private CompanyTypeService companyTypeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                Integer companyTypeId = Integer.parseInt(string);
                CompanyType companyType = companyTypeService.findByPK(companyTypeId);
                return companyType;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof CompanyType) {
            CompanyType companyType = (CompanyType) o;
            return Integer.toString(companyType.getId());
        }
        return null;
    }

}
