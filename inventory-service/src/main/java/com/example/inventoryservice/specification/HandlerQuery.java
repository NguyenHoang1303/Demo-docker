package com.example.inventoryservice.specification;

import com.example.inventoryservice.constant.Operation;
import com.example.inventoryservice.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;

@Log4j2
public class HandlerQuery {

    public static Specification creatQuery(ObjectFilter filter) {

        HashMap<String, String> mapField = FieldProduct.createdField();
        Specification specification = Specification.where(null);
        int id = filter.getId();
        if (id < 0) {
            throw new NotFoundException("Product is not found! Please check the information again.");
        } else if (id > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldProduct.ID),
                    Operation.EQUAL,
                    filter.getId())));
        }

        if (filter.getName() != null && filter.getName().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldProduct.NAME),
                    Operation.EQUAL,
                    filter.getName())));
        }

        int minPrice = filter.getMinPrice();
        if (minPrice < 0) {
            throw new NotFoundException("Product is not found! Please check the information again.");
        } else if (minPrice > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldProduct.PRICE),
                    Operation.GREATER_THAN_OR_EQUAL_TO, filter.getMinPrice())));
        }

        int maxPrice = filter.getMaxPrice();
        if (maxPrice < 0) {
            throw new NotFoundException("Product is not found! Please check the information again.");
        } else if (maxPrice > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldProduct.PRICE),
                    Operation.lESS_THAN_OR_EQUAL_TO, filter.getMaxPrice())));
        }

        return specification;
    }

    public static Pageable creatPagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, FieldProduct.CREATED_AT);
    }
}
