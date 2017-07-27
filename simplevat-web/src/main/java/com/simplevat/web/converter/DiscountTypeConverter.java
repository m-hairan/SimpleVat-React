package com.simplevat.web.converter;

import com.simplevat.entity.invoice.DiscountType;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author hiren
 */
@FacesConverter(forClass = DiscountType.class, value = "discountTypeConverter")
public class DiscountTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            DiscountType discountType = new DiscountType();

            discountType.setDiscountTypeCode(Integer.parseInt(string));
            return discountType;
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
