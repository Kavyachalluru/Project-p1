package com.revshop.p1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.p1.entity.OrderItems;
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
	  default List<OrderItems> findByOrder_Buyer_Id(Long buyerId) {
		// TODO Auto-generated method stub
		return null;
	} // Fe
}
