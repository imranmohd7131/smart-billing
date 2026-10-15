package com.imran.billing.smart_billing.repository;

import com.imran.billing.smart_billing.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByBranchIdOrderBySaleDateDesc(Long branchId);

    @Query("SELECT s.product.name, SUM(s.quantity) as totalQty FROM Sale s GROUP BY s.product.name ORDER BY totalQty DESC")
    List<Object[]> findTopSellingProducts();

    @Query("SELECT s.product.name, SUM(s.quantity) as totalQty FROM Sale s WHERE s.branch.id = :branchId GROUP BY s.product.name ORDER BY totalQty DESC")
    List<Object[]> findTopSellingByBranch(Long branchId);
}
