/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import lombok.Data;

/**
 *
 * @author h
 */
@Data
public class InvoiceTemplateModel {

    private String name;
    private String value;

    public InvoiceTemplateModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public InvoiceTemplateModel() {
    }

}
