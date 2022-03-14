package com.example.orderservice.dto;


import com.example.orderservice.entity.Order;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private Long orderId;
    private Long userId;
    private Set<OrderDetailDto> orderDetails = new HashSet<>();
    private BigDecimal totalPrice;
    private String paymentStatus;
    private String inventoryStatus;
    private String orderStatus;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUserId();
        this.totalPrice = order.getTotalPrice();
        this.paymentStatus = order.getPaymentStatus();
        this.inventoryStatus = order.getInventoryStatus();
        this.orderStatus = order.getOrderStatus();
        order.getOrderDetails().forEach(orderDetail -> {
            this.orderDetails.add(new OrderDetailDto(orderDetail));
        });
    }
}
