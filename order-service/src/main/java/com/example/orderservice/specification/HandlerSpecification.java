package com.example.orderservice.specification;

import com.example.orderservice.constant.Operation;
import com.example.orderservice.util.HandlerDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

@Log4j2
public class HandlerSpecification implements Specification<Order> {

    private final SearchCriteria criteria;

    public HandlerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String operation = criteria.getOperation();
        String key = criteria.getKey();
        Object value = criteria.getValue();
        log.info("operation: " + operation);
        log.info("key: " + key);
        log.info("value: " + value);
        switch (operation) {
            case Operation.GREATER_THAN_OR_EQUAL_TO:
                if (key.equalsIgnoreCase(FieldOrder.CREATED_AT.getValue())) {
                    LocalDate date = HandlerDate.convertStringToLocalDate(value.toString());
                    return builder.greaterThanOrEqualTo(root.get(FieldOrder.CREATED_AT.getValue()), date);
                }
                return builder.greaterThanOrEqualTo(root.get(key), value.toString());

            case Operation.lESS_THAN_OR_EQUAL_TO:
                if (key.equalsIgnoreCase(FieldOrder.CREATED_AT.getValue())) {
                    LocalDate date = HandlerDate.convertStringToLocalDate(criteria.getValue().toString());
                    return builder.lessThanOrEqualTo(root.get(FieldOrder.CREATED_AT.getValue()), date);
                }
                return builder.lessThanOrEqualTo(root.get(key), value.toString());

            case Operation.EQUAL:
                if (root.get(key).getJavaType() == String.class) {
                    return builder.like(root.get(key), Operation.LIKE + value + Operation.LIKE);
                }
                return builder.equal(root.get(key), value);
        }

        return null;
    }
}
