package com.simplevat.web.productservice.controller;

import com.simplevat.entity.Product;
import com.simplevat.web.productservice.model.ProductModel;

public class ProductModelHelper {

    public Product convertToProduct(ProductModel productModel) {
        Product product = new Product();
        product.setProductID(productModel.getProductID());
        product.setProductName(productModel.getProductName());
        product.setVatCategory(productModel.getVatCategory());
        product.setCreatedBy(productModel.getCreatedBy());
        product.setCreatedDate(productModel.getCreatedDate());
        product.setDeleteFlag(productModel.getDeleteFlag());
        product.setLastUpdateDate(productModel.getLastUpdateDate());
        product.setLastUpdatedBy(productModel.getLastUpdatedBy());
        product.setProductCode(productModel.getProductCode());
        product.setVersionNumber(productModel.getVersionNumber());
        product.setProductDescription(productModel.getProductDescription());
        return product;
    }

    public ProductModel convertToProductModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductID(product.getProductID());
        productModel.setProductName(product.getProductName());
        productModel.setVatCategory(product.getVatCategory());
        productModel.setCreatedBy(product.getCreatedBy());
        productModel.setCreatedDate(product.getCreatedDate());
        productModel.setDeleteFlag(product.getDeleteFlag());
        productModel.setLastUpdateDate(product.getLastUpdateDate());
        productModel.setLastUpdatedBy(product.getLastUpdatedBy());
        productModel.setProductCode(product.getProductCode());
        productModel.setVersionNumber(product.getVersionNumber());
        productModel.setProductDescription(product.getProductDescription());
        return productModel;
    }
}
