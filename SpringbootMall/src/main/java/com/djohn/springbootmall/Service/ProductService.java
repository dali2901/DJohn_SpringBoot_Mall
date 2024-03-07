package com.djohn.springbootmall.Service;


import com.djohn.springbootmall.Dto.ProductQueryParams;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;

import java.util.List;

public interface ProductService {


    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

}




