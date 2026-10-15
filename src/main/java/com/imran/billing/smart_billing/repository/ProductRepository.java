package com.imran.billing.smart_billing.repository;

import com.imran.billing.smart_billing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
