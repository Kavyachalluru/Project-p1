package com.revshop.p1.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.repository.BuyerRepository;


@Service
public class BuyerService {
	
	@Autowired
	private BuyerRepository buyer_repo;
		
	public Buyer registerUser(Buyer buyer) {
		buyer.setRegistrationDate(LocalDateTime.now());
		return buyer_repo.save(buyer);
	}
	
}
