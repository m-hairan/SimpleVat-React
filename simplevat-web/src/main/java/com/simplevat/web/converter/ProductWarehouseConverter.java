package com.simplevat.web.converter;

import com.simplevat.entity.ProductWarehouse;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.service.ProductWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bhavin.panchani
 *
 */
@Service
public class ProductWarehouseConverter implements Converter {

    @Autowired
    private ProductWarehouseService productWarehouseService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                ProductWarehouse productWarehouse = productWarehouseService.findByPK(Integer.parseInt(string));
                return productWarehouse;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

        if (o instanceof ProductWarehouse) {
            ProductWarehouse productWarehouse = (ProductWarehouse) o;
            return productWarehouse.getWarehouseId().toString();
        }
        return null;
    }

}
