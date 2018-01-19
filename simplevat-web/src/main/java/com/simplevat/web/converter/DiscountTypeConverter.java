package com.simplevat.web.converter;

import com.simplevat.entity.invoice.DiscountType;
import com.simplevat.service.DiscountTypeService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hiren
 */
@Service
public class DiscountTypeConverter implements Converter {

    @Autowired
    private DiscountTypeService discountTypeService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                DiscountType discountType = discountTypeService.findByPK(Integer.parseInt(string));
                return discountType;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof DiscountType) {
            DiscountType discountType = (DiscountType) o;
            return Integer.toString(discountType.getDiscountTypeCode());
        }
        return o.toString();
    }

}
