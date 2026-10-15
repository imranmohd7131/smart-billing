package com.imran.billing.smart_billing.service;

import com.imran.billing.smart_billing.entity.*;
import com.imran.billing.smart_billing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final SaleRepository saleRepo;
    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;

    @Value("${inventory.low-stock-threshold:10}")
    private int lowStockThreshold;

    public List<Inventory> getInventoryByBranch(Long branchId) {
        return inventoryRepo.findByBranchId(branchId);
    }

    public List<Inventory> getLowStockAlerts() {
        return inventoryRepo.findLowStock(lowStockThreshold);
    }

    @Transactional
    public void updateStock(Long inventoryId, int quantity) {
        Inventory inv = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inv.setQuantity(quantity);
        inventoryRepo.save(inv);
    }

    @Transactional
    public Sale processSale(Long branchId, Long productId, int qty, String customerName) {
        Inventory inv = inventoryRepo.findByBranchId(branchId).stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not in this branch"));

        if (inv.getQuantity() < qty)
            throw new RuntimeException("Insufficient stock. Available: " + inv.getQuantity());

        inv.setQuantity(inv.getQuantity() - qty);
        inventoryRepo.save(inv);

        Sale sale = new Sale();
        sale.setBranch(inv.getBranch());
        sale.setProduct(inv.getProduct());
        sale.setQuantity(qty);
        sale.setTotalAmount(inv.getProduct().getPrice().multiply(BigDecimal.valueOf(qty)));
        sale.setCustomerName(customerName);
        return saleRepo.save(sale);
    }

    @Transactional
    public Inventory addInventory(Long branchId, Long productId, int quantity) {
        return inventoryRepo.findByBranchIdAndProductId(branchId, productId)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + quantity);
                    return inventoryRepo.save(existing);
                })
                .orElseGet(() -> {
                    Branch branch = branchRepo.findById(branchId).orElseThrow();
                    Product product = productRepo.findById(productId).orElseThrow();
                    Inventory inv = new Inventory();
                    inv.setBranch(branch);
                    inv.setProduct(product);
                    inv.setQuantity(quantity);
                    return inventoryRepo.save(inv);
                });
    }

    public List<Sale> getSalesByBranch(Long branchId) {
        return saleRepo.findByBranchIdOrderBySaleDateDesc(branchId);
    }
}
