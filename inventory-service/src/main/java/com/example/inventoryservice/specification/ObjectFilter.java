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


    public static final class ObjectFilterBuilder {
        private int id;
        private int minPrice;
        private int supplierId;
        private int maxPrice;
        private int page;
        private int pageSize;
        private String name;
        private HashMap<String, String> mapField;

        private ObjectFilterBuilder() {
        }

        public static ObjectFilterBuilder anObjectFilter() {
            return new ObjectFilterBuilder();
        }

        public ObjectFilterBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public ObjectFilterBuilder withMinPrice(int minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public ObjectFilterBuilder withSupplierId(int supplierId) {
            this.supplierId = supplierId;
            return this;
        }

        public ObjectFilterBuilder withMaxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ObjectFilterBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public ObjectFilterBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public ObjectFilterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ObjectFilterBuilder withMapField(HashMap<String, String> mapField) {
            this.mapField = mapField;
            return this;
        }

        public ObjectFilter build() {
            ObjectFilter objectFilter = new ObjectFilter();
            objectFilter.setId(id);
            objectFilter.setMinPrice(minPrice);
            objectFilter.setSupplierId(supplierId);
            objectFilter.setMaxPrice(maxPrice);
            objectFilter.setPage(page);
            objectFilter.setPageSize(pageSize);
            objectFilter.setName(name);
            objectFilter.setMapField(mapField);
            return objectFilter;
        }
    }
}
