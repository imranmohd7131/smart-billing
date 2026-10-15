package com.imran.billing.smart_billing.service;

import com.imran.billing.smart_billing.dto.DashboardStats;
import com.imran.billing.smart_billing.entity.Inventory;
import com.imran.billing.smart_billing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;
    private final SaleRepository saleRepo;

    @Value("${inventory.low-stock-threshold:10}")
    private int lowStockThreshold;

    public DashboardStats getStats() {
        BigDecimal todayRevenue = saleRepo.findAll().stream()
                .filter(s -> s.getSaleDate().toLocalDate().equals(LocalDate.now()))
                .map(s -> s.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Object[]> topRaw = saleRepo.findTopSellingProducts();
        List<Map<String, Object>> topSelling = new ArrayList<>();
        for (int i = 0; i < Math.min(5, topRaw.size()); i++) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("product", topRaw.get(i)[0]);
            m.put("qty", topRaw.get(i)[1]);
            topSelling.add(m);
        }

        List<Map<String, Object>> branchSummary = new ArrayList<>();
        branchRepo.findAll().forEach(branch -> {
            int total = inventoryRepo.findByBranchId(branch.getId())
                    .stream().mapToInt(Inventory::getQuantity).sum();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("branch", branch.getName());
            m.put("totalStock", total);
            branchSummary.add(m);
        });

        return new DashboardStats(
                branchRepo.count(),
                productRepo.count(),
                inventoryRepo.findLowStock(lowStockThreshold).size(),
                todayRevenue,
                topSelling,
                branchSummary
        );
    }
}
