package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Constant.ProductCategory;
import com.djohn.springbootmall.Dto.ProductQueryParams;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;

import java.util.List;

public interface ProductDao {



    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById( Integer productId);


    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
