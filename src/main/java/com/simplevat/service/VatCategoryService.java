package com.simplevat.service;

import com.simplevat.criteria.ProjectCriteria;
import com.simplevat.entity.Project;
import com.simplevat.entity.VatCategory;
import java.util.List;


public abstract class VatCategoryService extends SimpleVatService<Integer, VatCategory> {

	public abstract List<VatCategory> getVatCategoryList();
        public abstract List<VatCategory> getVatCategorys(String name);

        public abstract VatCategory getDefaultVatCategory();

}