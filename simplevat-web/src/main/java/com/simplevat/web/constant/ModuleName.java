/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.constant;

/**
 *
 * @author uday
 */
public enum ModuleName {
    INVOICE_MODULE("INVOICE_MODULE"), 
    EXPENSE_MODULE ("EXPANSE_MODULE"),
    TAX_MODULE ("TAX_MODULE"),
    BANK_MODULE ("BANK_MODULE"),
    PURCHASE_MODULE ("PURCHASE_MODULE"),
    PROJECT_MODULE ("PROJECT_MODULE"),
    CONTACT_MODULE("CONTACT_MODULE"),
    SETTING_MODULE ("SETTING_MODULE"),
    REPORT_MODULE ("REPORT_MODULE");
   
    
    String moduleName;
    private ModuleName(String moduleName){
        this.moduleName = moduleName;
    }
    
    public String getName(){
        return moduleName;
    }
}
