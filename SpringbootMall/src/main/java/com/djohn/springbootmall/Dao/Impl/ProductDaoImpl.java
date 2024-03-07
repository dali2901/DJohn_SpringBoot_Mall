package com.djohn.springbootmall.Dao.Impl;

import com.djohn.springbootmall.Dao.ProductDao;
import com.djohn.springbootmall.Dto.ProductQueryParams;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.RowMapper.ProductRowMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoImpl implements ProductDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public ProductDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        String  sql = "SELECT COUNT(*) FROM product WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        //--------------------------------查詢條件--------------------------------(提煉出來，當每次需要拼接sql語句時拜託addFilteringSql方法去拼接)

        sql = addFilteringSql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql = "SELECT product_id, product_name,  category , image_url,  price,  stock, description ," +
                "created_date, last_modified_date FROM product WHERE 1=1";
        // 上方加上一個條件 WHERE 1=1 是 為了彈性的使用下方  "AND category = :category" 這段SQL
        //若category = null  ，WHERE 1=1 對SQL沒有任何影響 (查全部商品數據)
        //若category != null，就會變成" WHERE 1=1 +  AND category = :category" 這段SQL ( Enum帶過來的category數據)

        Map<String, Object> map = new HashMap<>();

        //--------------------------------查詢條件--------------------------------  (提煉出來，當每次需要拼接sql語句時拜託addFilteringSql方法去拼接)
        sql = addFilteringSql(sql, map, productQueryParams);


        //--------------------------------排序--------------------------------
        sql = sql +" ORDER BY " + productQueryParams.getOrderBy() + " "+productQueryParams.getSort();
        // SPRING JDBC Template  使用 ORDER BY 時 只能用字串拼接的方式，不能用上方  : sql變數的方式



        //--------------------------------分頁--------------------------------
        //LIMIT跟OFFSET會接在 ORDER BY的語句後面
        sql = sql +" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());


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

        int productId = keyHolder.getKey().intValue();

        return productId;
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
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate" +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_Id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);
    }


    private  String addFilteringSql(String sql , Map<String, Object> map, ProductQueryParams productQueryParams){

        //註 : 這個方法本來寫在 countProduct 跟 getProducts 裡面 但因為重複
        // 我們要將他提煉出來，使他能重複使用 並且提高維護性


        //--------------------------------查詢條件--------------------------------
        if(productQueryParams.getCategory() != null){
            sql =sql +" AND category = :category"; //AND 前面要+空白鍵 ，這樣拼接才不會連在一起
            map.put("category", productQueryParams.getCategory().name());
        }

        if( productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search"; //AND 前面要+空白鍵 ，這樣拼接才不會連在一起
            map.put("search", "%"+productQueryParams.getSearch()+"%");
        }

        return  sql;
    }


}
