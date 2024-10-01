package com.revshop.p1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.p1.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
		
	// Get All Products By SellerId
	List<Product> findBySellerId(Long id);
	
	// Update Product By ProductId and SellerId
	Optional<Product> findByIdAndSellerId(Long id, Long sellerId);
	
}
