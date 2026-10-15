package com.imran.billing.smart_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data @AllArgsConstructor
public class DashboardStats {
    private long totalBranches;
    private long totalProducts;
    private long lowStockCount;
    private BigDecimal todayRevenue;
    private List<Map<String, Object>> topSellingProducts;
    private List<Map<String, Object>> branchStockSummary;
}
