/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.vat.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.VatCategoryService;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author daynil
 */
@Controller
@SpringScopeView

public class VatController {

    @Autowired
    private VatCategoryService vatCategoryService;

    @Getter
    @Setter
    VatCategory category;

    @PostConstruct
    public void init() {

        Object objSelectedVat = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("vatCategoryId");
        if (objSelectedVat != null) {
            category = vatCategoryService.findByPK(Integer.parseInt(objSelectedVat.toString()));
        } else {

            category = new VatCategory();
        }
    }

    public String save() {
        if (category.getId() == null) {
            vatCategoryService.persist(category);
        } else {
            category.setId(category.getId());
            vatCategoryService.update(category);
        }
        return "index?faces-redirect=true";
    }

}
