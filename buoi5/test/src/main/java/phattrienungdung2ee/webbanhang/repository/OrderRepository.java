package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdung2ee.webbanhang.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}