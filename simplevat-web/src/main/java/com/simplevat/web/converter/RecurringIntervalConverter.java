/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.converter;

import com.simplevat.web.constant.RecurringNameValueMapping;
import com.simplevat.web.utils.RecurringUtility;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class RecurringIntervalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            try {
                return getRecurringNameValueMappingByValue(Integer.parseInt(string));
            } catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof RecurringNameValueMapping) {
            System.out.println("=============="+o);
            RecurringNameValueMapping recurringName = (RecurringNameValueMapping) o;
            return recurringName.getValue().toString();
        }
        return null;
    }

    private RecurringNameValueMapping getRecurringNameValueMappingByValue(int i) {
        List<RecurringNameValueMapping> list = new RecurringUtility().completeRecurringInterval();
        for (RecurringNameValueMapping valueMaping : list) {
            if (i == valueMaping.getValue()) {
                return valueMaping;
            }
        }
        return null;
    }
}
