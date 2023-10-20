package com.djohn.springbootmall.RowMapper;

import com.djohn.springbootmall.Constant.ProductCategory;
import com.djohn.springbootmall.Model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {

        Product product = new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));


        // 因資料庫取出的資料是String類型，但現在我們需要Product是 ProductCategory的Enum類型
        String categoryString = resultSet.getString("category");
        // 把字串轉成Enum類型
        ProductCategory category = ProductCategory.valueOf(categoryString);   //根據傳進去字串的值，找尋ProductCategory裡面的固定值
        //再傳到Product的Set方法裡面
        product.setCategory(category);

//        product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));   可以寫成一行



        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

             return product;
    }
}
