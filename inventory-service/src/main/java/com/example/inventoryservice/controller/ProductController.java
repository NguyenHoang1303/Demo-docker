package com.example.inventoryservice.controller;


import com.example.inventoryservice.entity.Product;
import com.example.inventoryservice.response.RESTPagination;
import com.example.inventoryservice.response.RESTResponse;
import com.example.inventoryservice.service.ProductServiceImpl;
import com.example.inventoryservice.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;


    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed("view_product")
    public ResponseEntity getAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "pageSize", defaultValue = "9") int pageSize,
                                 @RequestParam(name = "id", defaultValue = "0") int id,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "minPrice", defaultValue = "0") int minPrice,
                                 @RequestParam(name = "maxPrice", defaultValue = "0") int maxPrice,
                                 @RequestParam(name = "supplierId", defaultValue = "0") int supplierId
    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withId(id)
                .withName(name)
                .withSupplierId(supplierId)
                .withPageSize(pageSize)
                .withPage(page)
                .withMaxPrice(maxPrice)
                .withMinPrice(minPrice)
                .build();
        Page paging = productService.findAll(filter);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);
    }

    @RolesAllowed("create_product")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.save(product))
                .build(), HttpStatus.OK);
    }

    @RolesAllowed("edit_product")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity edit(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.update(product))
                .build(), HttpStatus.OK);
    }

    @RolesAllowed("delete_product")
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity delete(@PathVariable int id) {
       if (productService.delete(id)){
           return new ResponseEntity<>(new RESTResponse.Success()
                   .build(), HttpStatus.OK);
       }
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setMessage("xóa thất bại")
                .build(), HttpStatus.FOUND);
    }
}
