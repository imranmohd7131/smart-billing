package com.imran.billing.smart_billing.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
@Table(name = "app_users")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role { OWNER, STAFF }
}
