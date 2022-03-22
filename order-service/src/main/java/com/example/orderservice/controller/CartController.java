package com.example.orderservice.controller;

import com.example.orderservice.entity.CartItem;
import com.example.orderservice.response.RESTResponse;
import com.example.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/carts")
@CrossOrigin("*")
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addToCart(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(cartService.addToCart(cartItem))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity clear() {
        cartService.clear();
        return new ResponseEntity<>(new RESTResponse.Success()
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "detail")
    public ResponseEntity getDetail() {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(cartService.getDetail())
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestParam(name = "productId") int productId,
                                 @RequestParam(name = "quantity") int quantity) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(cartService.updateCart(productId, quantity))
                .build(), HttpStatus.OK);
    }


}
