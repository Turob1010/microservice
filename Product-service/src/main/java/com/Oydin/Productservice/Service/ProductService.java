package com.Oydin.Productservice.Service;

import com.Oydin.Productservice.Entity.Product;
import com.Oydin.Productservice.Repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    public Product getById(Integer productId){
        return productRepository.findById(productId).get();
    }
    public List<Product> getAll() { return productRepository.findAll(); }

    public Product updateProduct(Product product){
        Product product1 = productRepository.save(product);
        return product1;
    }

    public void delete (Product product){
        productRepository.delete(product);
    }


}
