/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
public enum InvoiceNumberReferenceEnum {

    DDMMYY("{YYMMDD}", "DDYYMM"),
    CONTACT_CODE("{contactCode}", "Contact Code");

    private String value;
    private String description;

    InvoiceNumberReferenceEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static List<InvoiceNumberReferenceEnum> getInvoiceNumberReferenceEnumList() {
        List<InvoiceNumberReferenceEnum> referenceVariables = new ArrayList<>();
        referenceVariables.addAll(Arrays.asList(values()));
        return referenceVariables;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
