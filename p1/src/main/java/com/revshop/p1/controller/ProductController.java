package com.revshop.p1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.Product;
import com.revshop.p1.entity.Seller;
import com.revshop.p1.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/revshop")
public class ProductController {

	@Autowired
    private ProductService productService; 
    
	@GetMapping("/product")
	public String showProductForm(Model model, HttpSession session) {
	    model.addAttribute("product", new Product());
	    return "product_form"; 
	}

	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute Product product, HttpSession session) {
	    // Retrieve the seller from the session
	    Seller seller = (Seller) session.getAttribute("loggedInUser"); 
	    product.setSeller(seller); 
	    productService.addProduct(product); 
	    
	    return "redirect:/revshop/show"; // Redirect back to the product display
	}
	
	@GetMapping("/show")
	public String displayProductsForSeller(Model model, HttpSession session) {
		Seller seller = (Seller) session.getAttribute("loggedInUser");
		List<Product> products = productService.getAllProducts(seller.getId());
		model.addAttribute("products", products);
		return "SellerDashBoard";
	}
	
	
	// Show form to update the product by ID
	@GetMapping("/product/update")
    public String showUpdateForm(@RequestParam("productId") Long id, Model model, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        if (seller == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        
        Product product = productService.getProductByIdAndSeller(id, seller.getId());
        if (product != null) {
            model.addAttribute("product", product);
            return "products/UpdateProductBySeller"; 
        }
        return "error/403"; 
    }

    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("productId") Long id, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        
        if (seller != null) {
            product.setId(id); // Ensure the product ID is set
            product.setSeller(seller);
            
            try {
                productService.updateProduct(product); // Update product in the database
                return "redirect:/revshop/show"; // Redirect after successful update
            } catch (Exception e) {
                e.printStackTrace(); // Log the error for debugging
                return "error/500"; // Redirect to an error page
            }
        }
        
        return "error/403"; 
    }

    
    // Delete a product by ID
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
        Seller seller = (Seller) session.getAttribute("loggedInUser");
        Product product = productService.getProductByIdAndSeller(id, seller.getId());
        if (product != null) {
            productService.deleteProductById(id);
            return "redirect:/revshop/show";
        }
        return "error/403"; 
    }
    @GetMapping("/displayProducts")
    public String showProducts(Model model,HttpSession session) {
    	 Buyer buyer=(Buyer)session.getAttribute("loggedInUser");
    	 System.out.println(buyer.getBuyer_id());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "showProducts";  
    }
    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetails";  // This view shows individual product details
    }

    }
	
