package com.simplevat.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.VatCategoryDao;
import com.simplevat.entity.VatCategory;

@Repository
public class VatCategoryDaoImpl extends AbstractDao<Integer, VatCategory>  implements VatCategoryDao{

    @Override
    public List<VatCategory> getVatCategoryList() {
        List<VatCategory> vatCategoryList = this.executeNamedQuery("allVatCategory");
        return vatCategoryList;
    }
}
