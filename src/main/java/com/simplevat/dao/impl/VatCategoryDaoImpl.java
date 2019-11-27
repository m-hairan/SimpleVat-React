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
    public List<VatCategory> getVatCategorys(String name) {
        TypedQuery<VatCategory> query = getEntityManager().createQuery("SELECT v FROM VatCategory v  where v.deleteFlag = FALSE AND v.name LIKE '%'||:searchToken||'%' order by v.defaultFlag DESC, v.orderSequence ASC", VatCategory.class);
        query.setParameter("searchToken", name);
        List<VatCategory> vatCategorys = query.getResultList();
        if (vatCategorys != null && !vatCategorys.isEmpty()) {
            return vatCategorys;
        }
        return null;
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

    @Override
    public void deleteByIds(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            for (Integer id : ids) {
                VatCategory vatCategory = findByPK(id);
                vatCategory.setDeleteFlag(Boolean.TRUE);
                update(vatCategory);
            }
        }
    }
}
