package com.example.inventoryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price ")
    private BigDecimal price;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "unit_in_stock")
    private Integer unitInStock;

    @Column(name = "status")
    private int status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public void setInfo(Product newInfo) {

        this.name = newInfo.getName();
        this.price = newInfo.getPrice();
        this.description = newInfo.getDescription();
        this.thumbnail = newInfo.getThumbnail();
        this.detail = newInfo.getDetail();
        this.updatedAt = LocalDate.now();
    }

}
