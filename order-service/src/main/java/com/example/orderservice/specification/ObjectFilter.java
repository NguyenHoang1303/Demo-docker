package com.example.orderservice.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ObjectFilter {

    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String ID = "id";
    public static final String MAX_PRICE = "maxPrice";
    public static final String MIN_PRICE = "minPrice";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    private int id;
    private int categoryId;
    private int minPrice;
    private int maxPrice;
    private int page;
    private int pageSize;
    private String name;
    private String nameProduct;
    private String email;
    private String phone;
    private String from;
    private String to;
    private String paymentStatus;
    private String inventoryStatus;
    private HashMap<String, String> mapField;


}
