package com.Oydin.Productservice.Controller;
import com.Oydin.Productservice.Entity.Product;
import com.Oydin.Productservice.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@Slf4j
public class ProductResource {



    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/saveproduct")
    public Product saveProduct(@RequestBody Product product) {
        log.info("Inside saveProduct method of ProductResource");
        return productService.createProduct(product);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        log.info("Inside getProductById method of ProductResource");
        return productService.getById(productId);
    }

    @GetMapping("/getall")
    public List<Product> gerAll() {
        log.info("Inside getAll method of ProductResource");
        return productService.getAll();
    }


    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        log.info("Inside updateProduct method of ProductResource");
        Product product1 = productService.updateProduct(product);
        return product1;
    }

    @DeleteMapping("/delete/{productId}")
    public void  delete(@PathVariable Integer productId) {
        log.info("Inside delete method of ProductResource");
        productService.delete(productService.getById(productId));

    }

}
