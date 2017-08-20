package com.simplevat.service;

import java.util.List;

import javax.annotation.Nonnull;

import com.simplevat.entity.invoice.DiscountType;

/**
 *
 * @author hiren
 */
public abstract class DiscountTypeService extends SimpleVatService<Integer, DiscountType> {
    
    @Nonnull
    public abstract List<DiscountType> getDiscountTypes();
    
}
