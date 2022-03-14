package com.example.inventoryservice.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ObjectFilter {

    private int id;
    private int minPrice;
    private int supplierId;
    private int maxPrice;
    private int page;
    private int pageSize;
    private String name;
    private HashMap<String, String> mapField;


}
