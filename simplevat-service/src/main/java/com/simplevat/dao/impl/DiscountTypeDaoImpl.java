package com.simplevat.dao.impl;

import com.simplevat.dao.DiscountTypeDao;
import com.simplevat.entity.invoice.DiscountType;
import java.util.List;
import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hiren
 */
@Repository
public class DiscountTypeDaoImpl implements DiscountTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Nonnull
    public List<DiscountType> getDiscountTypes() {
        final List<DiscountType> discountTypes = entityManager
                .createNamedQuery("DiscountType.discountTypes", DiscountType.class)
                .getResultList();
        return discountTypes;
    }

}
