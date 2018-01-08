/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import com.simplevat.web.constant.RecurringNameValueMapping;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class RecurringUtility {

    public List<RecurringNameValueMapping> completeRecurringInterval() {
        List<RecurringNameValueMapping> list = new ArrayList<>();
        list.add(new RecurringNameValueMapping("Daily", 1));
        list.add(new RecurringNameValueMapping("Weekly", 2));
        list.add(new RecurringNameValueMapping("Monthly", 3));
        list.add(new RecurringNameValueMapping("Yearly", 4));
        return list;
    }

    public List<RecurringNameValueMapping> completeWeekRecurring() {
        List<RecurringNameValueMapping> list = new ArrayList<>();
        list.add(new RecurringNameValueMapping("Sunday", 1));
        list.add(new RecurringNameValueMapping("Monday", 2));
        list.add(new RecurringNameValueMapping("Tuseday", 3));
        list.add(new RecurringNameValueMapping("Wednesday", 4));
        list.add(new RecurringNameValueMapping("Thursday", 5));
        list.add(new RecurringNameValueMapping("Friday", 6));
        list.add(new RecurringNameValueMapping("Saturday", 7));
        return list;
    }

    public List<RecurringNameValueMapping> completeDaysRecurring() {
        List<RecurringNameValueMapping> list = new ArrayList<>();
        list.add(new RecurringNameValueMapping("Day", 0));
        list.add(new RecurringNameValueMapping("First", 1));
        list.add(new RecurringNameValueMapping("Second", 2));
        list.add(new RecurringNameValueMapping("Third", 3));
        list.add(new RecurringNameValueMapping("Fourth", 4));
        list.add(new RecurringNameValueMapping("Last", 5));
        return list;
    }

    public List<RecurringNameValueMapping> completeNoneByAfterRecurring() {
        List<RecurringNameValueMapping> list = new ArrayList();
        list.add(new RecurringNameValueMapping("None", 1));
        list.add(new RecurringNameValueMapping("By", 2));
        list.add(new RecurringNameValueMapping("After", 3));
        return list;
    }

    public List<RecurringNameValueMapping> completeMonthRecurring() {
        List<RecurringNameValueMapping> list = new ArrayList();
        list.add(new RecurringNameValueMapping("Jan", 1));
        list.add(new RecurringNameValueMapping("Feb", 2));
        list.add(new RecurringNameValueMapping("March", 3));
        list.add(new RecurringNameValueMapping("April", 4));
        list.add(new RecurringNameValueMapping("May", 5));
        list.add(new RecurringNameValueMapping("June", 6));
        list.add(new RecurringNameValueMapping("July", 7));
        list.add(new RecurringNameValueMapping("Aug", 8));
        list.add(new RecurringNameValueMapping("Sept", 9));
        list.add(new RecurringNameValueMapping("Oct", 10));
        list.add(new RecurringNameValueMapping("Nov", 11));
        list.add(new RecurringNameValueMapping("Dec", 12));
        return list;
    }

    public List<RecurringNameValueMapping> completeFirstToLastRecurring() {
        List<RecurringNameValueMapping> list = new ArrayList();
        list.add(new RecurringNameValueMapping("1st", 1));
        list.add(new RecurringNameValueMapping("2nd", 2));
        list.add(new RecurringNameValueMapping("3rd", 3));
        for (Integer i = 4; i < 30; i++) {
            list.add(new RecurringNameValueMapping(i.toString() + "th", i));
        }
        list.add(new RecurringNameValueMapping("Last", 0));
        return list;
    }
     
}
