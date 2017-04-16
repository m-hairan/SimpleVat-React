package com.simplevat.service;

import com.simplevat.entity.invoice.DiscountType;
import java.util.List;
import javax.annotation.Nonnull;

/**
 *
 * @author hiren
 */
public interface DiscountTypeService {
    
    @Nonnull
    List<DiscountType> getDiscountTypes();
    
}
