package com.simplevat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.VatCategoryDao;
import com.simplevat.entity.VatCategory;
import javax.persistence.TypedQuery;

@Repository
public class VatCategoryDaoImpl extends AbstractDao<Integer, VatCategory> implements VatCategoryDao {

    @Override
    public List<VatCategory> getVatCategoryList() {
        List<VatCategory> vatCategoryList = this.executeNamedQuery("allVatCategory");
        return vatCategoryList;
    }

    @Override
    public VatCategory getDefaultVatCategory() {
        TypedQuery<VatCategory> query = getEntityManager().createQuery("SELECT v FROM VatCategory v WHERE v.deleteFlag = false AND v.defaultFlag = 'Y'", VatCategory.class);
        List<VatCategory> vatCategory = query.getResultList();
        if (vatCategory != null && !vatCategory.isEmpty()) {
            return vatCategory.get(0);
        }
        return null;
    }
}
