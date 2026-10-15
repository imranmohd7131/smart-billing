package com.imran.billing.smart_billing.config;

import com.imran.billing.smart_billing.entity.*;
import com.imran.billing.smart_billing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) return;

        AppUser owner = new AppUser();
        owner.setUsername("owner");
        owner.setPassword(passwordEncoder.encode("owner123"));
        owner.setRole(AppUser.Role.OWNER);
        userRepo.save(owner);

        AppUser staff = new AppUser();
        staff.setUsername("staff");
        staff.setPassword(passwordEncoder.encode("staff123"));
        staff.setRole(AppUser.Role.STAFF);
        userRepo.save(staff);

        Branch b1 = new Branch(); b1.setName("Main Godown"); b1.setLocation("Delhi");
        Branch b2 = new Branch(); b2.setName("North Branch"); b2.setLocation("Noida");
        branchRepo.save(b1); branchRepo.save(b2);

        Product p1 = new Product(); p1.setName("Samsung TV 43\""); p1.setCategory("Electronics"); p1.setPrice(new BigDecimal("28000"));
        Product p2 = new Product(); p2.setName("Cotton Shirt"); p2.setCategory("Clothing"); p2.setPrice(new BigDecimal("450"));
        Product p3 = new Product(); p3.setName("Laptop Stand"); p3.setCategory("Accessories"); p3.setPrice(new BigDecimal("1200"));
        Product p4 = new Product(); p4.setName("Bluetooth Speaker"); p4.setCategory("Electronics"); p4.setPrice(new BigDecimal("2500"));
        productRepo.save(p1); productRepo.save(p2); productRepo.save(p3); productRepo.save(p4);

        saveInv(b1, p1, 25); saveInv(b1, p2, 8); saveInv(b1, p3, 50); saveInv(b1, p4, 5);
        saveInv(b2, p1, 12); saveInv(b2, p2, 3); saveInv(b2, p3, 30); saveInv(b2, p4, 9);
    }

    private void saveInv(Branch b, Product p, int qty) {
        Inventory inv = new Inventory();
        inv.setBranch(b); inv.setProduct(p); inv.setQuantity(qty);
        inventoryRepo.save(inv);
    }
}
