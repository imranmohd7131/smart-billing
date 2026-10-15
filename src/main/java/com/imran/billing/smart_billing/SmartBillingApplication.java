package com.imran.billing.smart_billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SmartBillingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartBillingApplication.class, args);
    }
}

@Controller
class RootController {
    @GetMapping("/")
    public String root() { return "redirect:/dashboard"; }
}
