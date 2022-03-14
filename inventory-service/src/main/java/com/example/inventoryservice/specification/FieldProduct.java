package com.example.inventoryservice.specification;

import java.util.HashMap;

public class FieldProduct {

    public static final String CATEGORY_ID = "category_id";
    public static final String PRICE = "price";
    public static final String SUPPLIER_ID = "supplierId";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CREATED_AT = "createdAt";

    public static HashMap<String, String> createdField() {
        HashMap<String, String> mapOrder = new HashMap<>();
        mapOrder.put(NAME, NAME);
        mapOrder.put(ID, ID);
        mapOrder.put(PRICE, PRICE);
        mapOrder.put(CATEGORY_ID, CATEGORY_ID);
        mapOrder.put(SUPPLIER_ID, SUPPLIER_ID);
        return mapOrder;
    }


}
