/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "CURRENCY_CONVERSION")
@Data
public class CurrencyConversion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CURRENCY_CODE")
    private Integer currencyCode;

    @Basic
    @Column(name = "EXCHANGE_RATE", precision = 19, scale = 9)
    private BigDecimal exchangeRate;
}
