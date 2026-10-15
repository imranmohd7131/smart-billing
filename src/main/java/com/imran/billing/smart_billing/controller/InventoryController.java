package com.imran.billing.smart_billing.controller;

import com.imran.billing.smart_billing.repository.BranchRepository;
import com.imran.billing.smart_billing.repository.ProductRepository;
import com.imran.billing.smart_billing.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;

    @GetMapping
    public String list(@RequestParam(required = false) Long branchId, Model model) {
        model.addAttribute("branches", branchRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        if (branchId != null) {
            model.addAttribute("inventory", inventoryService.getInventoryByBranch(branchId));
            model.addAttribute("selectedBranch", branchId);
        }
        return "inventory";
    }

    @PostMapping("/add")
    public String addStock(@RequestParam Long branchId, @RequestParam Long productId,
                           @RequestParam int quantity, RedirectAttributes ra) {
        inventoryService.addInventory(branchId, productId, quantity);
        ra.addFlashAttribute("success", "Stock added successfully!");
        return "redirect:/inventory?branchId=" + branchId;
    }

    @PostMapping("/update/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam int quantity,
                              @RequestParam Long branchId, RedirectAttributes ra) {
        inventoryService.updateStock(id, quantity);
        ra.addFlashAttribute("success", "Stock updated!");
        return "redirect:/inventory?branchId=" + branchId;
    }
}
