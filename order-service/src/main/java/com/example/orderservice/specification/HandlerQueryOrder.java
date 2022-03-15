package com.example.orderservice.specification;

import com.example.orderservice.constant.Operation;
import com.example.orderservice.exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class HandlerQueryOrder {

    public static Specification creatQuery(ObjectFilter filter) {

        Specification specification = Specification.where(null);
        if (filter.getName() != null && filter.getName().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.NAME.getValue(),
                    Operation.EQUAL,
                    filter.getName())));
        }

        if (filter.getPaymentStatus() != null && filter.getPaymentStatus().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.PAYMENT_STATUS.getValue(),
                    Operation.EQUAL,
                    filter.getPaymentStatus())));
        }

        if (filter.getInventoryStatus() != null && filter.getInventoryStatus().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.INVENTORY_STATUS.getValue(),
                    Operation.EQUAL,
                    filter.getInventoryStatus())));
        }

        if (filter.getEmail() != null && filter.getEmail().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.EMAIL.getValue(),
                    Operation.EQUAL,
                    filter.getEmail())));
        }

        if (filter.getPhone() != null && filter.getPhone().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.PHONE.getValue(),
                    Operation.EQUAL,
                    filter.getPhone())));
        }

        int id = filter.getId();
        if (id < 0) {
            throw new NotFoundException("Order is not found! Please check the information again.");
        } else if (id > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.ID.getValue(),
                    Operation.EQUAL,
                    filter.getId())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.CREATED_AT.getValue(),
                    Operation.GREATER_THAN_OR_EQUAL_TO,
                    filter.getFrom())));
        }

        if (filter.getTo() != null && filter.getTo().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.CREATED_AT.getValue(),
                    Operation.lESS_THAN_OR_EQUAL_TO,
                    filter.getTo())));
        }

        int minPrice = filter.getMinPrice();
        if (minPrice < 0) {
            throw new NotFoundException("Order is not found! Please check the information again.");
        } else {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    FieldOrder.PRICE.getValue(),
                    Operation.GREATER_THAN_OR_EQUAL_TO, filter.getMinPrice())));
        }

        int maxPrice = filter.getMaxPrice();
        if (maxPrice < 0) {
            throw new NotFoundException("Order is not found! Please check the information again.");
        } else if (maxPrice > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                            FieldOrder.PRICE.getValue(),
                    Operation.lESS_THAN_OR_EQUAL_TO, filter.getMaxPrice())));
        }

        return specification;
    }

    public static Pageable creatPagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, FieldOrder.CREATED_AT.getValue());
    }
}
