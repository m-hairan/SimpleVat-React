package com.simplevat.invoice.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
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
