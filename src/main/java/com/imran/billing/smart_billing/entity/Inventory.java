package com.imran.billing.smart_billing.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "product_id"}))
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
