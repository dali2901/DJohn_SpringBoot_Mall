package com.djohn.springbootmall.Dto;

import com.djohn.springbootmall.Constant.ProductCategory;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

//決定前端要傳的參數  id 資料庫會自動生成，DATE型態資料讓SPRINGBOOT程式去設定

public class ProductRequest {

@NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    //資料庫設計上面，description允許為空值  所以就不用@NotNull
    private String description;

    public String getProductName() {
        return productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
