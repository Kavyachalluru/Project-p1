package com.revshop.p1.controller;

import com.revshop.p1.entity.Orders;
import com.revshop.p1.entity.Product;
import com.revshop.p1.entity.Seller;
import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.OrderItems;
import com.revshop.p1.service.EmailService;
import com.revshop.p1.service.OrderItemsService;
import com.revshop.p1.service.OrderService;
import com.revshop.p1.service.ProductService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private ProductService productService;;
    
    @Autowired
    private OrderItemsService ois;
    @Autowired
    private EmailService emailService;
    

    @ModelAttribute("order")
    public Orders getOrder() {
        return new Orders(); // Provide a new Orders object for the form
    }

    @GetMapping("/orderform")
    public String showOrderForm(@RequestParam Long productId, 
                                @RequestParam Double price,
                                @RequestParam(required = false) String productImage,
                                @RequestParam String productName,HttpSession session,
                                Model model) {
        Orders order = new Orders(); // Create a new order instance
        order.setOrderItems(new ArrayList<>());
        order.setTotalPrice(price);
        Product product=productService.getProductById(productId);
        session.setAttribute("product", product);
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
    		   // order.setUpiMethod(upiMethod);
    		    order.setBuyer(buyer); // Set the buyerId in the order
    		    OrderItems orderitems=new OrderItems();
    		    orderitems.setOrder(order);
    		    Product product=(Product)session.getAttribute("product");
    		    orderitems.setProduct(product);
    		    orderitems.setTotalPrice(totalPrice);
    		    orderitems.setQuantity(1);
    		    // Save the order to the database
    		    orderService.createOrder(order);
    		    String buyerEmail = order.getBuyer().getEmail();
    	        String subject = "Order Confirmation";
    	        String message = "Dear " + order.getBuyer().getFirstName()+ ",\n\n"
    	                       + "Thank you for your order! Your order ID is " + order.getId()
    	                       + ". We'll notify you once your order is shipped.\n\n"
    	                       + "Best regards,\nRevShop Team";

    	        emailService.sendOrderConfirmationEmail(buyerEmail, subject, message);

    		    List<OrderItems> orderItemsList = new ArrayList<>();
    		    orderItemsList.add(orderitems);
    		    System.out.println(orderItemsList);
    		    order.setOrderItems(orderItemsList);
    		    ois.saveorderitem(orderitems);
    		    // Optionally, add attributes to the model for rendering
    		    model.addAttribute("order", order);
    		   
    		    model.addAttribute("message", "Order placed successfully!");
    		    return "redirect:/revshop/displayProducts"; // Redirect to display products
    		}
    
    @GetMapping("/orderitems")
    public String getOrderItems(HttpSession session, Model model) {
        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
        
        if (buyer == null) {
            return "redirect:/revshop/login";
        }
        
        Long buyerId = buyer.getBuyer_id();
        List<OrderItems> orderItems = ois.getOrderItemsByBuyerId(buyerId);
        
        model.addAttribute("orderItems", orderItems); // Pass order items to the view
        return "orderitems"; // Your view name
    }


    
    @GetMapping("/orders")
    public String getSellerOrders(Model model, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        
        // Check if seller is not null
        if (seller == null) {
            // Handle case where seller is not logged in (e.g., redirect to login page)
            return "redirect:/login";
        }

        // Use the sellerId from the session or the path variable as needed
        List<Orders> orders = orderService.getOrdersBySeller(seller.getId());
        model.addAttribute("orders", orders);
        return "products/ViewOrdersBySeller";  
    }

             
    }

