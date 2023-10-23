package com.djohn.springbootmall.Service;

import com.djohn.springbootmall.Constant.ProductCategory;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;

import java.util.List;

public interface ProductService {




    List<Product> getProducts(ProductCategory category,String  search);
    Product getProductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

}




