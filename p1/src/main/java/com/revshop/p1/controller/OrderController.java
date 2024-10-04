package com.revshop.p1.controller;

import com.revshop.p1.entity.Orders;
import com.revshop.p1.entity.Product;
import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.OrderItems;
import com.revshop.p1.service.OrderService;
import com.revshop.p1.service.ProductService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/revshop") // Base URL for all endpoints in this controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @ModelAttribute("order")
    public Orders getOrder() {
        return new Orders(); // Provide a new Orders object for the form
    }

    @GetMapping("/orderform")
    public String showOrderForm(@RequestParam Long productId, 
                                @RequestParam Double price,
                                @RequestParam(required = false) String productImage,
                                @RequestParam String productName,
                                Model model) {
        Orders order = new Orders(); // Create a new order instance
        order.setOrderItems(new ArrayList<>());
        order.setTotalPrice(price);

        model.addAttribute("order", order); // Add the order object to the model
        model.addAttribute("productId", productId);
        model.addAttribute("price", price);
        model.addAttribute("productName",productName);
        // Optional: Check if productImage is present
        if (productImage != null) {
            model.addAttribute("productImage", productImage);
        } else {
            model.addAttribute("productImage", "default-image.png"); // Fallback or placeholder image
        }

        return "orders2"; // Return the view name
    }


    @PostMapping("/addorders")
    		public String submitOrder(
    		        @RequestParam Double totalPrice,
    		        @RequestParam String shippingAddress,
    		        @RequestParam String paymentMethod,
    		        @RequestParam String upiMethod,
    		        HttpSession session, // Inject HttpSession to access session attributes
    		        Model model) {

    		    // Retrieve buyerId from session
    		    Buyer buyer = (Buyer) session.getAttribute("loggedInUser"); // Make sure the key matches the one used in your session

    		    if (buyer == null) {
    		        model.addAttribute("message", "You need to log in to place an order.");
    		        return "redirect:/revshop/login"; // Redirect to login if buyer is not found in session
    		    }

    		    // Create a new order object
    		    Orders order = new Orders();
    		    order.setTotalPrice(totalPrice);
    		    order.setShippingAddress(shippingAddress);
    		    order.setPaymentMethod(paymentMethod);
    		    order.setUpiMethod(upiMethod);
    		    order.setBuyer(buyer); // Set the buyerId in the order

    		    // Save the order to the database
    		    orderService.createOrder(order);

    		    // Optionally, add attributes to the model for rendering
    		    model.addAttribute("order", order);
    		    model.addAttribute("message", "Order placed successfully!");
    		    return "redirect:/revshop/displayProducts"; // Redirect to display products
    		}
//    @PostMapping("/addorders")
//    public String submitOrder(
//            @RequestParam Double totalPrice,
//            @RequestParam String shippingAddress,
//            @RequestParam String paymentMethod,
//            @RequestParam String upiMethod,
//            @RequestParam Long productIds, // Pass product IDs for the order
//            HttpSession session,
//            Model model) {
//
//        // Retrieve the buyer from the session
//        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
//
//        if (buyer == null) {
//            model.addAttribute("message", "You need to log in to place an order.");
//            return "redirect:/revshop/login";
//        }
//
//        // Create a new order
//        Orders order = new Orders();
//        order.setTotalPrice(totalPrice);
//        order.setShippingAddress(shippingAddress);
//        order.setPaymentMethod(paymentMethod);
//        order.setUpiMethod(upiMethod);
//        order.setBuyer(buyer);
//
//        List<OrderItems> orderItemsList = new ArrayList<>();
//
//        // Iterate through products and create order items
//  
//            Product product = productService.getProductById(productId); // Fetch the product
//
//            OrderItems orderItem = new OrderItems();
//            orderItem.setProduct(product);
//            orderItem.setQuantity(1); // Set quantity as needed
//            orderItem.setTotalPrice(product.getDiscountPrice()); // Or get price from product
//
//            // Associate the order item with the order
//            orderItem.setOrder(order);
//            orderItemsList.add(orderItem);
//        }
//
//        // Set the order items in the order
//        order.setOrderItems(orderItemsList);
//
//        // Save the order and its items
//        orderService.createOrder(order);
//
//        // Add attributes for confirmation
//        model.addAttribute("order", order);
//        model.addAttribute("message", "Order placed successfully!");
//        return "redirect:/revshop/displayProducts";
//    }

    
    @GetMapping("/orderitems")
    public String getOrderItems(HttpSession session, Model model) {
        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
        Long buyerId=buyer.getBuyer_id();
        if (buyer == null) {
            return "redirect:/revshop/login";
        }
        List<Orders> orders = orderService.getOrdersByBuyer(buyerId);
        model.addAttribute("orders", orders);
        return "orderitems";	
    }

             
    }

