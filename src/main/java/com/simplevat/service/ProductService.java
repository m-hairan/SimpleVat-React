package com.simplevat.service;

import com.simplevat.entity.Product;
import java.util.List;


public abstract class ProductService extends SimpleVatService<Integer, Product> {

	public abstract List<Product> getProductList();


}