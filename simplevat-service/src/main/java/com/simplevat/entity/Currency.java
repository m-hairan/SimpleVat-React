package com.simplevat.entity;

import com.simplevat.entity.BankAccount;
import com.simplevat.entity.Contact;
import com.simplevat.entity.Expense;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mohsinh on 2/26/2017.
 */
@Entity
@Table(name = "CURRENCY")
@Data
public class Currency {

    @Id
    @Column(name = "CURRENCY_CODE")
    private int currencyCode;
    @Basic
    @Column(name = "CURRENCY_NAME")
    private String currencyName;
    @Basic
    @Column(name = "CURRENCY_DESCRIPTION")
    private String currencyDescription;
    @Basic
    @Column(name = "CURRENCY_ISO_CODE")
    private String currencyIsoCode;
    @Basic
    @Column(name = "CURRENCY_SYMBOL")
    private String currencySymbol;
}
