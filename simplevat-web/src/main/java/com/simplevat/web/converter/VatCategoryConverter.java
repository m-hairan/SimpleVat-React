/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.VatCategory;
import com.simplevat.service.VatCategoryService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class VatCategoryConverter implements Converter {

    @Autowired
    VatCategoryService vatCategoryService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            try {
                VatCategory vatCategory = vatCategoryService.findByPK(Integer.parseInt(value));
                return vatCategory;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {

        if (object != null) {
            if (object instanceof VatCategory) {

                VatCategory vatCategory = (VatCategory) object;
                return Integer.toString(vatCategory.getId());
            }
        }
        return object.toString();
    }

}
