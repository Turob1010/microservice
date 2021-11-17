package com.Oydin.Productservice.Service;

import com.Oydin.Productservice.Entity.Product;
import com.Oydin.Productservice.Exception.ProductAlreadyExistsException;
import com.Oydin.Productservice.Exception.ProductNotFoundException;
import com.Oydin.Productservice.Repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceInt{

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsById(product.getProductId())) {
            throw new ProductAlreadyExistsException();
        }
        Product createProduct = productRepository.save(product);
        return createProduct;
    }

    @Override
    public Product updateProduct(Product productDetails, Integer productId) throws ProductNotFoundException {
        Product product;
        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFoundException();
        } else {
            product = productRepository.findById(productId).get();
            product.setProductId(productDetails.getProductId());
            product.setProductName(productDetails.getProductName());
            product.setPrice(productDetails.getPrice());
            Product updateProduct = productRepository.save(product);
            return updateProduct;
        }
    }

    public Product getById(Integer productId){
        return productRepository.findById(productId).get();
    }
    public List<Product> getAll() { return productRepository.findAll(); }



    public void delete (Product product){
        productRepository.delete(product);
    }


}
