package com.example.orderservice.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@ToString
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ship_name")
    private String shipName;
    @Column(name = "ship_phone")
    private String shipPhone;
    @Column(name = "ship_email")
    private String shipEmail;
    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "total_price")
    private double totalPrice;


}
