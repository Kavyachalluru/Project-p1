package com.example.Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Oder.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
