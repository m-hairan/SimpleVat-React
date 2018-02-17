/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.productservice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Product;
import com.simplevat.entity.ProductWarehouse;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.ProductService;
import com.simplevat.service.ProductWarehouseService;
import com.simplevat.service.VatCategoryService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.productservice.model.ProductModel;
import com.simplevat.web.utils.FacesUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
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
    @Autowired
    private ProductWarehouseService productWarehouseService;
    @Getter
    @Setter
    private ProductModel productModel;
    @Getter
    @Setter
    private ProductWarehouse productWarehouse;
    private ProductModelHelper productModelHelper;

    public ProductController() {
        super(ModuleName.PRODUCT_MODULE);
    }

    @PostConstruct
    public void init() {
        productWarehouse = new ProductWarehouse();
        productModel = new ProductModel();
        productModelHelper = new ProductModelHelper();
        Object objSelectedProductId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        if (objSelectedProductId != null) {
            Product product = productService.findByPK(Integer.parseInt(objSelectedProductId.toString()));
            productModel = productModelHelper.convertToProductModel(product);
        }

    }

    public List<VatCategory> vatCategorys(final String searchQuery) throws Exception {
        if (vatCategoryService.getVatCategoryList() != null) {
            return vatCategoryService.getVatCategoryList();
        }
        return null;
    }

    public List<Product> completeProducts() {
        List<Product> productList = productService.getProductList();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        return productList;
    }

    public List<ProductWarehouse> completeProductWarehouse() {
        List<ProductWarehouse> productWarehouseList = productWarehouseService.getProductWarehouseList();
        if (productWarehouseList == null) {
            productWarehouseList = new ArrayList<>();
        }
        return productWarehouseList;
    }

    public String saveProduct() {
        save();
        return "list?faces-redirect=true";
    }

    public String saveAndAddMoreProduct() {
        save();
        return "product?faces-redirect=true";

    }

    public void save() {
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
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (productModel.getProductID() == null) {
            productService.persist(product);
            context.addMessage(null, new FacesMessage("Successful", "Product and Service has been added"));
        } else {
            productService.update(product);
            context.addMessage(null, new FacesMessage("Successful", "Product and Service has been updated"));
        }

    }

    public void createNewWarehouse() {
        productWarehouseService.persist(productWarehouse);
        productModel.setProductWarehouse(productWarehouse);
        RequestContext.getCurrentInstance().execute("PF('add_new_warehouse').hide();");
        productWarehouse = new ProductWarehouse();
    }

}
