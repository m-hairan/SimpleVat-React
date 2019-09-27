package com.simplevat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ProductDao;
import com.simplevat.entity.Product;

@Repository
public class ProductDaoImpl extends AbstractDao<Integer, Product> implements ProductDao {

    @Override
    public List<Product> getProductList() {
        List<Product> products = this.executeNamedQuery("allProduct");
        return products;
    }
}
