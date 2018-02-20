/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.productservice.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Product;
import com.simplevat.service.ProductService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.productservice.model.ProductModel;
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

public class ProductListController extends BaseController {

    @Autowired
    private ProductService productService;

    @Getter
    @Setter
    private ProductModel productModel;

    @Getter
    @Setter
    private List<ProductModel> productModelList=new ArrayList<>();

    ProductModelHelper productModelHelper = new ProductModelHelper();

    public ProductListController() {
        super(ModuleName.PRODUCT_MODULE);
    }

    @PostConstruct
    public void init() {
        populateProductList();
    }

    public String productCreatePage() {
        return "product?faces-redirect=true";
    }

    public String editUser() {
        if (productModel.getProductID() != null) {
            return "product?faces-redirect=true&productId=" + productModel.getProductID();
        }

        return null;
    }

    public void deleteUser() {
        if (productModel.getProductID() != null) {
            Product product= productModelHelper.convertToProduct(productModel);
            product.setDeleteFlag(Boolean.TRUE);
            productService.update(product, product.getProductID());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Successful","Product/Service deleted SuccessFully"));
            init();
        }

    }

    private void populateProductList() {
        productModelList.clear();
        if (productService.getProductList()
                != null) {
            List<Product> products = productService.getProductList();
            if (products != null || !products.isEmpty()) {
                for (Product product : products) {
                    productModelList.add(new ProductModelHelper().convertToProductModel(product));
                }
            }
        }
    }

}
