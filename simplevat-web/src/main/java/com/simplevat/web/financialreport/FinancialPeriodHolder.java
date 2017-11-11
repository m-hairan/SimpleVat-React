/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.financialreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class FinancialPeriodHolder {

    private static final int INITIALYEAR = 2015;

    public static List<FinancialPeriod> getFinancialPeriodList() {
        List<FinancialPeriod> financialPeriodList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (year >= INITIALYEAR) {
            financialPeriodList.clear();
            for (int i = 0; i <= year - INITIALYEAR; i++) {
                calendar.set(Calendar.YEAR, INITIALYEAR+i);
                calendar.set(Calendar.MONTH, 00);
                calendar.set(Calendar.DAY_OF_MONTH, 01);
                Date startDate = calendar.getTime();
                calendar.set(Calendar.YEAR, INITIALYEAR+i);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                Date endDate = calendar.getTime();
                FinancialPeriod financialPeriod = new FinancialPeriod();
                financialPeriod.setId(i + 1);
                financialPeriod.setLastDate(endDate);
                financialPeriod.setStartDate(startDate);
                String name = new SimpleDateFormat("MM/dd/yyyy").format(startDate) + " To " + new SimpleDateFormat("MM/dd/yyyy").format(endDate);
                financialPeriod.setName(name);
                financialPeriodList.add(financialPeriod);
            }
        }
        return financialPeriodList;
    }
}
