package com.simplevat.web.converter;

import com.simplevat.entity.Purchase;
import com.simplevat.service.PurchaseService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class PurchaseConverter implements Converter {

    @Autowired
    private PurchaseService purchaseService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Purchase purchase = purchaseService.findByPK(Integer.parseInt(string));
            return purchase;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Purchase) {
            Purchase purchase = (Purchase) o;
            return purchase.getPurchaseId().toString();
        }
        return null;
    }

}
