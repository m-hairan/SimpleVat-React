/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.bankaccount.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author admin
 */
@Getter
@Setter
public class ReferenceObjectClass {

    private Integer id;
    private BigDecimal dueAmount;
    private Integer referenceType;
    private Integer status;

    public ReferenceObjectClass() {
    }

    public ReferenceObjectClass(Integer id, BigDecimal dueAmount, Integer referenceType, Integer status) {
        this.id = id;
        this.dueAmount = dueAmount;
        this.referenceType = referenceType;
        this.status = status;
    }

}
