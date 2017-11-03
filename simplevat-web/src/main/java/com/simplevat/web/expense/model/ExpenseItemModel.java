/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.expense.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author daynil
 */
@Getter
@Setter
public class ExpenseItemModel {

    private int id;
    private int quatity;
    private BigDecimal unitPrice ;
    private BigDecimal vatId = BigDecimal.ZERO;
    private String description;
    private BigDecimal subTotal;
    private Integer versionNumber;
}
