package com.imran.billing.smart_billing.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@Table(name = "branches")
public class Branch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;
}
