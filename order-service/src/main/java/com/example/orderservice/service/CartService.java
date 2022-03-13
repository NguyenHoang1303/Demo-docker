package com.example.orderservice.service;


import com.example.orderservice.entity.CartItem;

import java.util.HashMap;

public interface CartService {
    HashMap<Long, CartItem> addToCart(CartItem cartItem1);

    void clear();

    HashMap<Long, CartItem> getDetail();

    HashMap<Long, CartItem> updateCart(int productId, int quantity);
}
