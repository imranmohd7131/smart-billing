package com.imran.billing.smart_billing.repository;

import com.imran.billing.smart_billing.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByBranchId(Long branchId);

    Optional<Inventory> findByBranchIdAndProductId(Long branchId, Long productId);

    @Query("SELECT i FROM Inventory i WHERE i.quantity <= :threshold")
    List<Inventory> findLowStock(int threshold);

    @Query("SELECT i FROM Inventory i WHERE i.branch.id = :branchId AND i.quantity <= :threshold")
    List<Inventory> findLowStockByBranch(Long branchId, int threshold);
}
