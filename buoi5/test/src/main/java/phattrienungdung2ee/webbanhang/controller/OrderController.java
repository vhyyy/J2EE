package phattrienungdung2ee.webbanhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phattrienungdung2ee.webbanhang.service.CartService;
import phattrienungdung2ee.webbanhang.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    // Hiển thị form điền thông tin giao hàng
    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        if (cartService.getCartItems().isEmpty()) {
            return "redirect:/cart"; // Giỏ trống thì bắt quay lại
        }
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "checkout";
    }

    // Xử lý khi bấm nút "Xác nhận Đặt hàng"
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String customerName, 
                                  @RequestParam String phone, 
                                  @RequestParam String address) {
        orderService.placeOrder(customerName, phone, address);
        return "redirect:/order-success";
    }

    // Trang báo thành công
    @GetMapping("/order-success")
    public String successPage() {
        return "order-success";
    }
}