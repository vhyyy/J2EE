package phattrienungdung2ee.webbanhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phattrienungdung2ee.webbanhang.model.CartItem;
import phattrienungdung2ee.webbanhang.model.Order;
import phattrienungdung2ee.webbanhang.model.OrderDetail;
import phattrienungdung2ee.webbanhang.repository.OrderDetailRepository;
import phattrienungdung2ee.webbanhang.repository.OrderRepository;
import phattrienungdung2ee.webbanhang.repository.ProductRepository;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    public void placeOrder(String customerName, String phone, String address) {
        // 1. Lưu Hóa đơn tổng (Order)
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setTotalAmount(cartService.getTotalPrice());
        orderRepository.save(order);

        // 2. Lưu Chi tiết hóa đơn (OrderDetail)
        for (CartItem item : cartService.getCartItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(productRepository.findById(item.getProductId()).orElse(null));
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            orderDetailRepository.save(detail);
        }

        // 3. Thanh toán xong thì xóa giỏ hàng
        cartService.clearCart();
    }
}