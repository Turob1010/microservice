package com.Oydin.Productservice.Repository;

import com.Oydin.Productservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Integer> {
}
