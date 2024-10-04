package com.revshop.p1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.OrderItems;
import com.revshop.p1.entity.Orders;

import java.util.List;

@Repository
	public interface OrderRepository extends JpaRepository<Orders, Long> {
	    
	    // Find all orders by a specific buyer ID
//	    List<Orders> findByBuyer(Buyer buyer);
//	    List<Orders> findByBuyer_Id(Long buyer_id);
	@Query("SELECT o FROM Orders o WHERE o.buyer.id = :buyerId")
    List<Orders> findByBuyerId(@Param("buyerId") Long buyerId);
	   
	    
	   

	}



