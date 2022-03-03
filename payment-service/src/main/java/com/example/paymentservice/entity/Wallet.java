package com.example.paymentservice.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wallets")
@ToString
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private double balance;
    private String name;

}
