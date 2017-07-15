package com.simplevat.dao;

import com.simplevat.entity.invoice.DiscountType;
import java.util.List;
import javax.annotation.Nonnull;

/**
 *
 * @author hiren
 */
public interface DiscountTypeDao extends Dao<Integer, DiscountType> {
    
    
    @Nonnull
    List<DiscountType> getDiscountTypes();
    
}
