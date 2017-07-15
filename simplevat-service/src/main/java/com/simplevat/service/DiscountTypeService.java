package com.simplevat.service;

import java.util.List;

import javax.annotation.Nonnull;

import com.simplevat.entity.invoice.DiscountType;

/**
 *
 * @author hiren
 */
public interface DiscountTypeService extends SimpleVatService<Integer, DiscountType> {
    
    @Nonnull
    List<DiscountType> getDiscountTypes();
    
}
