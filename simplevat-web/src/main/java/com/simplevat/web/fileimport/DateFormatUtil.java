/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.fileimport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DateFormatUtil {
    public static List<String> dateFormatList() {
        List<String> dateFormats = new ArrayList<>();
        dateFormats.add("dd/MM/yyyy");
        dateFormats.add("yyyy/MM/dd");
        dateFormats.add("dd-MM-yyyy");
        dateFormats.add("dd-M-yyyy");
        dateFormats.add("MM-dd-yyyy");
        dateFormats.add("yyyy-MM-dd");
        dateFormats.add("dd MMMM yyyy");
        dateFormats.add("MM/dd/yyyy");
        return dateFormats;
    }
}
