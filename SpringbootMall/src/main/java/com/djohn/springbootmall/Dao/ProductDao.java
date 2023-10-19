package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Model.Product;

public interface ProductDao {


    Product getProductById( Integer productId);
}
