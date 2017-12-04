/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.productservice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Product;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.ProductService;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.productservice.model.ProductModel;
import com.simplevat.web.utils.FacesUtil;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class ProductController extends BaseController {

    @Autowired
    private VatCategoryService vatCategoryService;

    @Autowired
    private ProductService productService;
    private ProductModel productModel;
    private ProductModelHelper productModelHelper;

    public ProductController() {
        super(ModuleName.PRODUCT_MODULE);
    }

    @PostConstruct
    public void init() {
        productModel = new ProductModel();
        productModelHelper = new ProductModelHelper();
        Object objSelectedProductId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        if (objSelectedProductId != null) {
            Product product = productService.findByPK(Integer.parseInt(objSelectedProductId.toString()));
            productModel = productModelHelper.convertToProductModel(product);
        }

    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public List<VatCategory> vatCategorys(final String searchQuery) throws Exception {
        if (vatCategoryService.getVatCategoryList() != null) {
            return vatCategoryService.getVatCategoryList();
        }
        return null;
    }

    public String save() {
        Product product = productModelHelper.convertToProduct(productModel);
        if (productModel.getProductID() == null) {
            product.setCreatedBy(FacesUtil.getLoggedInUser().getCreatedBy());
            product.setCreatedDate(LocalDateTime.now());
            product.setDeleteFlag(Boolean.FALSE);
        } else {
            product.setLastUpdateDate(LocalDateTime.now());
            product.setLastUpdatedBy(FacesUtil.getLoggedInUser().getCreatedBy());
        }
        if (productModel.getVatCategory() != null) {
            VatCategory vatCategory = vatCategoryService.findByPK(productModel.getVatCategory().getId());
            product.setVatCategory(vatCategory);
        }
        if (productModel.getProductID() == null) {
            productService.persist(product);
        } else {
            productService.update(product);
        }
        return "list?faces-redirect=true";
    }

    public String saveAndAddMoreInvoice() {
        Product product = productModelHelper.convertToProduct(productModel);
        if (productModel.getProductID() == null) {
            product.setCreatedBy(FacesUtil.getLoggedInUser().getCreatedBy());
            product.setCreatedDate(LocalDateTime.now());
            product.setDeleteFlag(Boolean.FALSE);
        } else {
            product.setLastUpdateDate(LocalDateTime.now());
            product.setLastUpdatedBy(FacesUtil.getLoggedInUser().getCreatedBy());
        }
        if (productModel.getVatCategory() != null) {
            VatCategory vatCategory = vatCategoryService.findByPK(productModel.getVatCategory().getId());
            product.setVatCategory(vatCategory);
        }
        if (productModel.getProductID() == null) {
            productService.persist(product);
        } else {
            productService.update(product);
        }
        return "product?faces-redirect=true";

    }
}
