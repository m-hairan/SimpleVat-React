package com.simplevat.web.invoice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hiren
 */
@Getter
@Setter
public class InvoiceItemModel {
    
    private int id;

    private int quatity;
    private BigDecimal unitPrice;
    private BigDecimal vatId = BigDecimal.ZERO;
    private String description;
    private BigDecimal subTotal;
    private Integer versionNumber;
    private String productService;
    
}
