/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.entity.Product;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.ProductService;
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
public class ProductConverter implements Converter {

    @Autowired
    ProductService productService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            try {
                Product product = productService.findByPK(Integer.parseInt(value));
                return product;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {

        if (object != null) {
            if (object instanceof Product) {
                Product product = (Product) object;
                return Integer.toString(product.getProductID());
            }
        }
        return null;
    }

}
