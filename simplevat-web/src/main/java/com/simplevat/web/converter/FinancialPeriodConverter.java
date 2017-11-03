package com.simplevat.web.converter;

import com.simplevat.web.report.FinancialPeriod;
import com.simplevat.web.report.FinancialPeriodHolder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Service;

/**
 * @author hiren
 */
@Service
public class FinancialPeriodConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            FinancialPeriod financialPeriod = getFinancialPeriodById(Integer.parseInt(string));
            return financialPeriod;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof FinancialPeriod) {
            FinancialPeriod financialPeriod = (FinancialPeriod) o;
            return financialPeriod.getId().toString();
        }
        return null;
    }

    private FinancialPeriod getFinancialPeriodById(int id) {
        for (FinancialPeriod financialPeriod : FinancialPeriodHolder.getFinancialPeriodList()) {
            if (financialPeriod.getId() == id) {
                return financialPeriod;
            }
        }
        return new FinancialPeriod();
    }

}
