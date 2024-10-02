package com.revshop.controller;

import com.revshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/revshop")
public class OrderController {

    private final ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/revshop/orders")
    public String getOrders(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "orders";
    }
}
