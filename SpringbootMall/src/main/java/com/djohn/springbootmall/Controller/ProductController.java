package com.djohn.springbootmall.Controller;


import com.djohn.springbootmall.Constant.ProductCategory;
import com.djohn.springbootmall.Dto.ProductQueryParams;
import com.djohn.springbootmall.Dto.ProductRequest;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.Service.ProductService;
import com.djohn.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
        // 查詢條件 Filtering
        @RequestParam(required = false) ProductCategory category,
        @RequestParam(required = false) String search,
        //  排序  Sorting （控制商品排序）
        @RequestParam(defaultValue = "created_date") String orderBy,
        @RequestParam(defaultValue = "desc") String sort,
        //  排序  Pagination
        @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,  //只取幾筆數據
        @RequestParam(defaultValue = "0") @Min(0) Integer offset  //跳過幾筆數據
    ) {
//要使前端能夠依照URL輸入的category參數(CAR或FOOD)來當條件尋找商品，我們要把category送到Dao層，讓他根據category使用SQL語法尋找
// 並且帶上(required = false) 因為前端可以選擇要不要帶上category參數過來(有可能使用者不想分類而是想找全部商品)

//拿創建日期當作 defaultValue 因為通常會希望新的商品在最前面 (預設的條件的概念)
//拿desc當作 defaultValue 進行由大到小的排序

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);


        //下方這行是取得商品列表的數據
        List<Product> productsList = productService.getProducts(productQueryParams);

        //下方這行取得商品的總筆數
        Integer total = productService.countProduct(productQueryParams);

        //下方設定分頁的ResponseBody的值
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productsList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }


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

        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        //上面這段是為了當我成功在資料庫新增商品後，可以根據新增的商品Id用getProductById方法，查詢到這一筆資料

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        //先使用productId 嘗試查詢這筆商品數據是否存在 ，如有才修改數據，若沒有就回傳404
        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        productService.updateProduct(productId, productRequest);

        //修改商品成功後，用這個商品ID取查詢更新後的商品數據
        Product updateProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
        //這行要放updateProduct才是回傳給前端更新後的商品數據

    }

    @DeleteMapping("/products/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        Product product = productService.getProductById(productId);

        if (product == null) {
            String msg = "您要刪除商品不存在";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        } else {

            productService.deleteProductById(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }


    }

}
