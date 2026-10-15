package com.imran.billing.smart_billing.controller;

import com.imran.billing.smart_billing.entity.Product;
import com.imran.billing.smart_billing.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("product", new Product());
        return "products";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String category,
                      @RequestParam BigDecimal price, RedirectAttributes ra) {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        productRepo.save(p);
        ra.addFlashAttribute("success", "Product \"" + name + "\" added successfully!");
        return "redirect:/products";
    }
}
