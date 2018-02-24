package com.simplevat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.criteria.ProjectFilter;
import com.simplevat.dao.Dao;
import com.simplevat.dao.ProjectDao;
import com.simplevat.dao.VatCategoryDao;
import com.simplevat.entity.Activity;
import com.simplevat.entity.Project;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.ProjectService;
import com.simplevat.service.VatCategoryService;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
@Service("vatCategoryService")

public class VatCategoryServiceImpl extends VatCategoryService {

    @Autowired
    private VatCategoryDao vatCategoryDao;


    private static final String VAT_CATEGORY = "VAT_CATEGORY";

    public List<VatCategory> getVatCategoryList() {
        return vatCategoryDao.getVatCategoryList();
    }
    
    @Override
    public List<VatCategory> getVatCategorys(String name) {
        return vatCategoryDao.getVatCategorys(name);
    }

    @Override
    protected Dao<Integer, VatCategory> getDao() {
        return vatCategoryDao;
    }

    @Override
    public VatCategory getDefaultVatCategory() {
        return vatCategoryDao.getDefaultVatCategory();
    }

    
    public void persist(VatCategory vatCategory) {
        super.persist(vatCategory, null, getActivity(vatCategory, "CREATED"));
    }

    public VatCategory update(VatCategory vatCategory) {
        return super.update(vatCategory, null, getActivity(vatCategory, "UPDATED"));
    }

    private Activity getActivity(VatCategory vatCategory, String activityCode) {
        Activity activity = new Activity();
        activity.setActivityCode(activityCode);
        activity.setModuleCode(VAT_CATEGORY);
        activity.setField3("Vat Category" + activityCode.charAt(0) + activityCode.substring(1, activityCode.length()).toLowerCase());
        activity.setField1(vatCategory.getId().toString());
        activity.setField2(vatCategory.getName());
        activity.setLastUpdateDate(LocalDateTime.now());
        activity.setLoggingRequired(true);
        return activity;
    }

    

}
