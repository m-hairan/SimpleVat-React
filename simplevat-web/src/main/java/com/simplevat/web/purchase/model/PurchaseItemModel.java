/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.purchase.model;

import com.simplevat.entity.Product;
import com.simplevat.entity.VatCategory;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author admin
 */
@Getter
@Setter
public class PurchaseItemModel {

    private int id;
    private int quatity;
    private BigDecimal unitPrice;
    private VatCategory vatId;
    private String description;
    private BigDecimal subTotal;
    private Integer versionNumber;
    private Product productService;
}
