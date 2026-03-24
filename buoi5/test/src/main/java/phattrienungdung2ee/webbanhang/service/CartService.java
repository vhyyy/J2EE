package phattrienungdung2ee.webbanhang.service;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import phattrienungdung2ee.webbanhang.model.CartItem;
import phattrienungdung2ee.webbanhang.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    // Câu 5: Thêm vào giỏ hàng
    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(product.getId().longValue())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product.getId().longValue(), product.getName(), product.getPrice(), quantity));
    }

    // Lấy danh sách sản phẩm trong giỏ
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Câu 6: Tính tổng tiền
    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
    // Dùng cho Câu 7: Xóa sạch giỏ hàng sau khi thanh toán thành công
    public void clearCart() {
        cartItems.clear();
    }
}