package phattrienungdung2ee.webbanhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.service.CartService;
import phattrienungdung2ee.webbanhang.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    // Câu 6: Hiển thị trang giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart"; 
    }

    // Câu 5: Xử lý nút "Thêm vào giỏ"
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id, @RequestParam(defaultValue = "1") int quantity) {
        Product product = productService.get(id);
        if (product != null) {
            cartService.addToCart(product, quantity);
        }
        return "redirect:/cart"; // Thêm xong tự động nhảy sang trang Giỏ hàng
    }
}