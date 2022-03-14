package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Product;
import com.example.inventoryservice.specification.ObjectFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Product save(Product product);

    Product update(Product product);

    boolean delete(int productId);

    Product findById(long productId);

    Page findAll(ObjectFilter filter);

    List<Product> saveAll(Set<Product> products);
}
