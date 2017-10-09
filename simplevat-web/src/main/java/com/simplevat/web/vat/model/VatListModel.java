/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.vat.model;

import com.simplevat.entity.bankaccount.TransactionType;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author uday
 */
public class VatListModel {
    
     private Date vatDate;
     private TransactionType transactionType;
     private BigDecimal vat_in;
     private BigDecimal vat_out;
     private BigDecimal balance;

    public Date getVatDate() {
        return vatDate;
    }

    public void setVatDate(Date vatDate) {
        this.vatDate = vatDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getVat_in() {
        return vat_in;
    }

    public void setVat_in(BigDecimal vat_in) {
        this.vat_in = vat_in;
    }

    public BigDecimal getVat_out() {
        return vat_out;
    }

    public void setVat_out(BigDecimal vat_out) {
        this.vat_out = vat_out;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    

    
}
