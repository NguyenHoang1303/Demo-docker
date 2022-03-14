package com.example.orderservice.specification;

public enum FieldOrder {

    ID("id"),
    PRICE("totalPrice"),
    NAME("name"),
    PHONE("phone"),
    EMAIL("email"),
    CREATED_AT("createdAt"),
    PAYMENT_STATUS("paymentStatus"),
    INVENTORY_STATUS("inventoryStatus");

    private final String value;
    FieldOrder(String id) { this.value = id; }
    public String getValue() { return value; }


}
