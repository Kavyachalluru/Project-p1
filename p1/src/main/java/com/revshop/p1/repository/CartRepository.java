package com.revshop.p1.repository;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.Cart;
import com.revshop.p1.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Custom query to find all cart items for a specific buyer
    List<Cart> findByBuyer(Buyer buyer);
    Cart findByBuyerAndProduct(Buyer buyer, Product product);

    // Optionally, you can add more methods to find cart items by product, etc.
}
