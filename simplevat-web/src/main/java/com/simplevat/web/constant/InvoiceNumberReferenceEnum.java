/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.constant;

import java.util.ArrayList;
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

    private InvoiceNumberReferenceEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static List<InvoiceNumberReferenceEnum> getInvoiceNumberReferenceEnumList() {
        List<InvoiceNumberReferenceEnum> referenceVariables = new ArrayList<>();
        for (InvoiceNumberReferenceEnum invoiceReferenceVariable : values()) {
            referenceVariables.add(invoiceReferenceVariable);
        }
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
