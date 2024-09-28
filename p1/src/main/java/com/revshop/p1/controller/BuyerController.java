package com.revshop.p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.service.BuyerService;


@Controller
@RequestMapping("/revshop")
public class BuyerController {
	
	@Autowired
	private BuyerService buyerService;
	
	@GetMapping("/buyerRegister")
	public String showRegForm(Model model) {
		model.addAttribute("buyers", new Buyer());
		return "buyerRegister";
	}
	
	@PostMapping("/buyerRegister")
	public String registerBuyer(Model model, @ModelAttribute Buyer buyer) {
		buyerService.registerUser(buyer);
		return "revHome";
	}
	
	@GetMapping("/buyerLogin")
	public String showLoginForm(Model model) {
		model.addAttribute("buyers", new Buyer());
		return "revHome";
	}
	
	

}
