package com.djohn.springbootmall.Dao.Impl;

import com.djohn.springbootmall.Constant.ProductCategory;
import com.djohn.springbootmall.Dao.ProductDao;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.RowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProducts(ProductCategory category, String  search) {

        String sql = "SELECT product_id, product_name,  category , image_url,  price,  stock, description ," +
                "created_date, last_modified_date FROM product WHERE 1=1";
        // 上方加上一個條件 WHERE 1=1 是 為了彈性的使用下方  "AND category = :category" 這段SQL
        //若category = null  ，WHERE 1=1 對SQL沒有任何影響 (查全部商品數據)
        //若category != null，就會變成" WHERE 1=1 +  AND category = :category" 這段SQL ( Enum帶過來的category數據)

        Map<String, Object> map = new HashMap<>();

        if(category != null){
            sql =sql +" AND category = :category"; //AND 前面要+空白鍵 ，這樣拼接才不會連在一起
            map.put("category", category.name());
        }

        if( search != null){
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%"+search+"%");
        }

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;

    }

    @Override
    public Product getProductById(Integer productId) {

        String sql = "SELECT product_id, product_name,  category , image_url,  price,  stock, description ," +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();

        map.put("productId",productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }else {
            return null;
        }
    }


    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT INTO product(product_name, category,image_url,price,stock," +
                "description, created_date, last_modified_date)"+
                "VALUES(:productName, :category, :imageUrl, :price, :stock, :description," +
                ":createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map), keyHolder);

        int prodcutId = keyHolder.getKey().intValue();

        return prodcutId;
    }


    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "UPDATE product SET product_name = :productName,  category = :category," +
                "image_url = :imageUrl, price = :price, stock = :stock, description = :description, last_modified_date =  :lastModifiedDate "+
                "WHERE product_Id = :productId";
        //上方放了一個 要修改 last_modified_date，因為要返回修改商品數據的時間給前端


        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date updateTime = new Date();
        map.put("lastModifiedDate", updateTime);

        namedParameterJdbcTemplate.update(sql,map);
    }


    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_Id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
