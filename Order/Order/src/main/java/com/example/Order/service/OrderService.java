package com.example.Order.service;

import com.example.Oder.model.Product;
import com.example.Oder.model.User;
import com.example.Order.repository.ProductRepository;
import com.example.Order.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Product getProduct(Long productID) {
        return productRepository.findById(productID).orElse(null);
    }

    public User getUser(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public void placeOrder(Long userID, Long productID, String shippingAddress) {
        // Implement order placement logic, like saving to an Order table or sending notifications
    }
}
