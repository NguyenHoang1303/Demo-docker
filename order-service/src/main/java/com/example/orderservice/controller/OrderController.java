package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.response.RESTPagination;
import com.example.orderservice.response.RESTResponse;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.specification.FieldOrder;
import com.example.orderservice.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Order order) {
        return new ResponseEntity<>(
                new RESTResponse.Success()
                        .addData(orderService.create(order))
                        .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "pageSize", defaultValue = "6") int pageSize,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "nameProduct", required = false) String nameProduct,
                                 @RequestParam(name = "id", defaultValue = "0") int id,
                                 @RequestParam(name = "from", required = false) String from,
                                 @RequestParam(name = "to", required = false) String to,
                                 @RequestParam(name = "email", required = false) String email,
                                 @RequestParam(name = "phone", required = false) String phone,
                                 @RequestParam(name = "minPrice", defaultValue = "0") int minPrice,
                                 @RequestParam(name = "maxPrice", defaultValue = "0") int maxPrice,
                                 @RequestParam(name = "paymentStatus", required = false) String paymentStatus,
                                 @RequestParam(name = "inventoryStatus", required = false) String inventoryStatus
    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withId(id)
                .withPageSize(pageSize).withPage(page)
                .withMaxPrice(maxPrice).withMinPrice(minPrice)
                .withPhone(phone).withName(name).withEmail(email)
                .withNameProduct(nameProduct)
                .withFrom(from).withTo(to)
                .withInventoryStatus(inventoryStatus).withPaymentStatus(paymentStatus)
                .build();
        Page<Order> paging = orderService.getAll(filter);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "detail")
    public ResponseEntity finById(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.findById((long) id))
                .buildData(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "user/detail")
    public ResponseEntity getOrderByUserId(@RequestParam(name = "userId") int userId) {
        userId= 451691;
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.findOrderByUserId(userId))
                .buildData(), HttpStatus.OK);
    }

    @RolesAllowed("view_orders")
    @GetMapping("{id}/detail")
    public ResponseEntity findOrderDetailByOrderId(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.getOrderDetailByOrderId(id))
                .build(), HttpStatus.OK);
    }

    @RolesAllowed("edit_orders")
    @PutMapping("status/update")
    public ResponseEntity updateStatus(@Valid
                                       @RequestParam int id,
                                       @RequestParam String status) {

        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.updateStatus(id, status))
                .build(), HttpStatus.OK);
    }

    @RolesAllowed("delete_orders")
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.delete(id))
                .build(), HttpStatus.OK);
    }

}
