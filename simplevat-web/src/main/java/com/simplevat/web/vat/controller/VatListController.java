/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.vat.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView

public class VatListController extends BaseController implements Serializable{

    @Getter
    @Setter
    VatCategory vatCategory;

    @Getter
    @Setter
    List<VatCategory> vatCategoryList = new ArrayList<>();

    @Autowired
    private VatCategoryService vatCategoryService;

    public VatListController() {
        super(ModuleName.SETTING_MODULE);
    }
   @PostConstruct
    public void init() {

        if (vatCategoryService.getVatCategoryList() != null) {
            vatCategoryList = vatCategoryService.getVatCategoryList();
        }
    }

    public String getVatcategoryPage() {
        System.out.println("FacesContext=================="+FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap());
        return "createVatcategory?faces-redirect=true";
    }

    public String editVatcategory() {
        if (vatCategory.getId() != null) {
            return "createVatcategory?faces-redirect=true&vatCategoryId=" + vatCategory.getId();
        }
        return null;
    }

    public void deleteVatcategory() {

        if (vatCategory.getId() != null) {
            vatCategory.setDeleteFlag(true);
            vatCategoryService.update(vatCategory, vatCategory.getId());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("VatCategory deleted SuccessFully"));
            init();
        }
    }

}
