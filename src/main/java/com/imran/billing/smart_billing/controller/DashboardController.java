package com.imran.billing.smart_billing.controller;

import com.imran.billing.smart_billing.repository.InventoryRepository;
import com.imran.billing.smart_billing.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final InventoryRepository inventoryRepo;

    @Value("${inventory.low-stock-threshold:10}")
    private int threshold;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getStats());
        model.addAttribute("lowStockItems", inventoryRepo.findLowStock(threshold));
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
