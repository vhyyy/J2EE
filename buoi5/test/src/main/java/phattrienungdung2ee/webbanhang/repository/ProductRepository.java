package phattrienungdung2ee.webbanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phattrienungdung2ee.webbanhang.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Hàm xử lý Câu 1 (Tìm kiếm) và Câu 4 (Lọc) kết hợp Phân trang (Câu 2)
    @Query("SELECT p FROM Product p WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> searchAndFilter(@Param("keyword") String keyword, 
                                  @Param("categoryId") Long categoryId, 
                                  Pageable pageable);
}