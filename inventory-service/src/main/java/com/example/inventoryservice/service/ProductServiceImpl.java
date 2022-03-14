package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Product;
import com.example.inventoryservice.repository.ProductRepository;
import com.example.inventoryservice.specification.HandlerQuery;
import com.example.inventoryservice.specification.ObjectFilter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public Page findAll(ObjectFilter filter) {
        return productRepo.findAll(HandlerQuery.creatQuery(filter),
                HandlerQuery.creatPagination(filter.getPage(), filter.getPageSize()));
    }

    @Override
    public List<Product> saveAll(Set<Product> products) {
        return productRepo.saveAll(products);
    }

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product update(Product newItem) {
        try {
            Product product = productRepo.getById(newItem.getId());
            product.setInfo(newItem);
            return productRepo.save(product);
        } catch (Exception e) {
            throw new RuntimeException("System fail, please try again.");
        }
    }

    @Override
    public boolean delete(int productId) {
        try {
            Product product = productRepo.getById((long) productId);
            product.setStatus(-1);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Product findById(long productId) {
        return productRepo.findById(productId).orElse(null);
    }
}
