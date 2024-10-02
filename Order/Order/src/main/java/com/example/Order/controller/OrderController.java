package com.example.Order.controller;

import com.example.Oder.model.Product;
import com.example.Oder.model.User;
import com.example.Order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order") 
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/place-order") 
    public String placeOrder(@RequestParam Long userID, @RequestParam Long productID, Model model) {
       
        User user = orderService.getUser(userID);
        Product product = orderService.getProduct(productID);
        
        
        if (user == null || product == null) {
            return "error";
        }

        // Add user and product to the model
        model.addAttribute("user", user);
        model.addAttribute("product", product);
        
        // Return the view name for the place order page (Thymeleaf template)
        return "place_order"; 
    }

    @PostMapping("/confirm-order") // Maps to /order/confirm-order
    public String confirmOrder(@RequestParam Long userID,
                               @RequestParam Long productID,
                               @RequestParam String shippingAddress,
                               @RequestParam String paymentMethod,
                               @RequestParam(required = false) String upiMethod) {
        // Process the order here
        orderService.placeOrder(userID, productID, shippingAddress);
        
        // Redirect to a success page
        return "redirect:/order-success"; // Ensure this endpoint exists
    }
}
