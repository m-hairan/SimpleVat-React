/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.invoice.controller;

import java.math.BigDecimal;

/**
 *
 * @author daynil
 */
public class InvoiceDataSourceModel {

    String quantity;
    String details;
    Double unitPrice;
    Double vat;
    Double netSubtotal;

    public InvoiceDataSourceModel(String quantity, String details, Double unitPrice, Double vat, Double netSubtotal) {
        this.quantity = quantity;
        this.details = details;
        this.unitPrice = unitPrice;
        this.vat = vat;
        this.netSubtotal = netSubtotal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getNetSubtotal() {
        return netSubtotal;
    }

    public void setNetSubtotal(Double netSubtotal) {
        this.netSubtotal = netSubtotal;
    }
}
