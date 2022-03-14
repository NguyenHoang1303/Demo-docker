package com.example.inventoryservice.specification;

import com.example.inventoryservice.constant.Operation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

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
        switch (operation) {
            case Operation.GREATER_THAN_OR_EQUAL_TO:
                return builder.greaterThanOrEqualTo(root.get(key), value.toString());

            case Operation.lESS_THAN_OR_EQUAL_TO:
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
