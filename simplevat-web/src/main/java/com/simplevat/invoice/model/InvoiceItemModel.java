package com.simplevat.invoice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hiren
 */
@Getter
@Setter
public class InvoiceItemModel {

    private int quatity;

    private BigDecimal unitPrice;

    private BigDecimal vatId;

    private String description;

    private BigDecimal subTotal;

}
