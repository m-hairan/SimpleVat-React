package com.simplevat.utils;

import java.text.DateFormatSymbols;

/**
 * @author Ankit
 *
 */
public class DateUtils {
	
	/**
	 * @param num
	 * @return
	 */
	public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
