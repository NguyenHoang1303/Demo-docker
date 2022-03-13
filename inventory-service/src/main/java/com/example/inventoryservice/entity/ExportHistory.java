package com.example.inventoryservice.entity;


import event.OrderDetailEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "export_history")
public class ExportHistory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private int quantity;
    private Long productId;
    private LocalDate createdAt;

    public ExportHistory(OrderDetailEvent orderDetail, Long orderId) {
        this.orderId = orderId;
        this.quantity = orderDetail.getQuantity();
        this.productId = orderDetail.getProductId();
        this.createdAt = LocalDate.now();
    }
}
