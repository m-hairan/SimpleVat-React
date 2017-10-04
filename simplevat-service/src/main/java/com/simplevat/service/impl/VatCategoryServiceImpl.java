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
import com.simplevat.entity.Project;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.ProjectService;
import com.simplevat.service.VatCategoryService;
import java.util.ArrayList;

/**
 * Created by Utkarsh Bhavsar on 21/03/17.
 */
@Service("vatCategoryService")

public class VatCategoryServiceImpl   extends VatCategoryService {

    @Autowired
    private VatCategoryDao vatCategoryDao;

    
    
   @Override
    public List<VatCategory> getVatCategoryList() {
        
        /*List<SelectItem> vatList = new ArrayList<>();
        List<VatCategory> vatCategoryList =  vatCategoryDao.getVatCategoryList();
        for (VatCategory vatCategory : vatCategoryList) {
        SelectItem item=new SelectItem(vatCategory.getVat(), vatCategory.getName());
        vatList.add(item);
        }*/
        
        return vatCategoryDao.getVatCategoryList();
    }

    @Override
    protected Dao<Integer, VatCategory> getDao() {
      return vatCategoryDao;
    }

    
    


    
	
  
}
