package com.simplevat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ProductDao;
import com.simplevat.entity.Product;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

@Repository
public class ProductDaoImpl extends AbstractDao<Integer, Product> implements ProductDao {

    @Cacheable(value = "productCache", key = "#product.productID")
    @Override
    public List<Product> getProductList() {
        System.out.println("getProductList calling:");
        List<Product> products = this.executeNamedQuery("allProduct");
        return products;
    }

    @Caching(
            put = {
                @CachePut(value = "productCache", key = "#product.productID", condition = "#product != null")
            }
    )
    public Product findByPK(int pk) {
         System.out.println(" findByPK == called");
        return findById(pk);
    }

    @Cacheable(value = "userCache", unless = "#result != null")
    public Product findById(int pk) {
        System.out.println(" findById == called");
        return super.findByPK(pk);
    }
}
