package com.djohn.springbootmall.Controller;


import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        //@RequestBody 註解 目的為了接住前端傳過來的JSON資料
        //因為我們在ProductRequest (Dto) 去寫上NOTNULL註解去驗證前端的請求參數 ，所以這裡要放@Valid 才會生效

        Integer productId =  productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        //上面這段是為了當我成功在資料庫新增商品後，可以根據新增的商品Id用getProductById方法，查詢到這一筆資料

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }


}
