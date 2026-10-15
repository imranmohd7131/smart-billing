package com.imran.billing.smart_billing.controller;

import com.imran.billing.smart_billing.entity.Sale;
import com.imran.billing.smart_billing.repository.BranchRepository;
import com.imran.billing.smart_billing.repository.ProductRepository;
import com.imran.billing.smart_billing.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final InventoryService inventoryService;
    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;

    @GetMapping
    public String billingPage(@RequestParam(required = false) Long branchId, Model model) {
        model.addAttribute("branches", branchRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        if (branchId != null) {
            model.addAttribute("sales", inventoryService.getSalesByBranch(branchId));
            model.addAttribute("selectedBranch", branchId);
        }
        return "billing";
    }

    @PostMapping("/sell")
    public String processSale(@RequestParam Long branchId, @RequestParam Long productId,
                              @RequestParam int quantity, @RequestParam String customerName,
                              RedirectAttributes ra) {
        try {
            Sale sale = inventoryService.processSale(branchId, productId, quantity, customerName);
            ra.addFlashAttribute("success",
                    "Sale recorded! Bill Amount: ₹" + sale.getTotalAmount());
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/billing?branchId=" + branchId;
    }
}
