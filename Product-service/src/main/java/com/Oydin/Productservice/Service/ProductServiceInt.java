package com.Oydin.Productservice.Service;

import com.Oydin.Productservice.Entity.Product;
import com.Oydin.Productservice.Exception.ProductAlreadyExistsException;
import com.Oydin.Productservice.Exception.ProductNotFoundException;

public interface ProductServiceInt {

    Product createProduct(Product product) throws ProductAlreadyExistsException;
    Product updateProduct(Product productDetails,Integer productId) throws ProductNotFoundException;
}
