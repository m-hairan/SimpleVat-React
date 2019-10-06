/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class FinancialPeriodHolderRest {

    private static final int INITIALYEAR = 2015;

    public static List<FinancialPeriodRest> getFinancialPeriodList() {
        List<FinancialPeriodRest> financialPeriodList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int id = 1;
        int monthCount = 2;
        int monthDecrement = 0;
        if (year >= INITIALYEAR) {
            financialPeriodList.clear();
            FinancialPeriodRest financialCustomPeriod = new FinancialPeriodRest();
            financialCustomPeriod.setId(id);
            financialCustomPeriod.setLastDate(null);
            financialCustomPeriod.setStartDate(null);
            financialCustomPeriod.setName("Custom");
            financialPeriodList.add(financialCustomPeriod);
            id++;
            for (int i = 0; i <= year - INITIALYEAR; i++) {
                String name = "";
                FinancialPeriodRest financialPeriod = new FinancialPeriodRest();
                if (i == 0) {
                    FinancialPeriodRest financialPeriod1 = new FinancialPeriodRest();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.YEAR, year - i);
                    calendar1.set(Calendar.DAY_OF_MONTH, 01);
                    Date startCurrentMonthDate = calendar1.getTime();
                    calendar1.set(Calendar.YEAR, year - i);
                    calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMaximum(Calendar.DATE));
                    Date endCurrentMonthDate = calendar1.getTime();
                    financialPeriod1.setId(id);
                    financialPeriod1.setLastDate(endCurrentMonthDate);
                    financialPeriod1.setStartDate(startCurrentMonthDate);
                    name = "Current Month";
                    financialPeriod1.setName(name);
                    financialPeriodList.add(financialPeriod1);
                    id++;
                    Calendar calendar2 = Calendar.getInstance();
                    FinancialPeriodRest financialPeriod2 = new FinancialPeriodRest();
                    calendar2.set(Calendar.YEAR, year - i);
                    calendar2.add(Calendar.MONTH, -1);
                    calendar2.set(Calendar.DAY_OF_MONTH, 01);
                    Date startPreviousMonthDate = calendar2.getTime();
                    calendar2.set(Calendar.YEAR, year - i);
                    calendar2.add(Calendar.MONTH, 0);
                    calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DATE));
                    Date endPreviousMonthDate = calendar2.getTime();
                    financialPeriod2.setId(id);
                    financialPeriod2.setLastDate(endPreviousMonthDate);
                    financialPeriod2.setStartDate(startPreviousMonthDate);
                    name = "Previous Month";
                    financialPeriod2.setName(name);
                    financialPeriodList.add(financialPeriod2);
                }
                id++;
                calendar.set(Calendar.YEAR, year - i);
                calendar.set(Calendar.MONTH, 00);
                calendar.set(Calendar.DAY_OF_MONTH, 01);
                Date startDate = calendar.getTime();
                calendar.set(Calendar.YEAR, year - i);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                Date endDate = calendar.getTime();
                financialPeriod.setId(id);
                financialPeriod.setLastDate(endDate);
                financialPeriod.setStartDate(startDate);

                if (i > 1) {
                    name = new SimpleDateFormat("MM/dd/yyyy").format(startDate) + " To " + new SimpleDateFormat("MM/dd/yyyy").format(endDate);
                } else if (i == 1) {
                    name = "Previous Year";
                } else {
                    name = "Current Year";
                }
                // name = new SimpleDateFormat("MM/dd/yyyy").format(startDate) + " To " + new SimpleDateFormat("MM/dd/yyyy").format(endDate);
                financialPeriod.setName(name);
                financialPeriodList.add(financialPeriod);

            }
        }
        return financialPeriodList;
    }
}
