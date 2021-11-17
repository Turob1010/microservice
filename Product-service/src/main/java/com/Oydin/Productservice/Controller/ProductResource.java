package com.Oydin.Productservice.Controller;
import com.Oydin.Productservice.Entity.Product;
import com.Oydin.Productservice.Exception.ProductAlreadyExistsException;
import com.Oydin.Productservice.Exception.ProductNotFoundException;
import com.Oydin.Productservice.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/save")
    public ResponseEntity saveProduct(@RequestBody Product product) throws ProductAlreadyExistsException {
        log.info("Inside saveProduct method of ProductResource");
        Product saveProduct = productService.createProduct(product);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }
    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    public ResponseEntity handleProductAlreadyExistsException(ProductAlreadyExistsException productAlreadyExistsException) {
        return new ResponseEntity("Product already exists :", HttpStatus.CONFLICT);
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


    @PutMapping("/update/{productId}")
    public Product updateProduct(@RequestBody Product productDetails, @PathVariable Integer productId) throws ProductNotFoundException {
        log.info("Inside updateProduct method of ProductResource");
        Product updateProduct = productService.updateProduct(productDetails,productId);
        return updateProduct;
    }

    @DeleteMapping("/delete/{productId}")
    public void  delete(@PathVariable Integer productId) {
        log.info("Inside delete method of ProductResource");
        productService.delete(productService.getById(productId));

    }

}
