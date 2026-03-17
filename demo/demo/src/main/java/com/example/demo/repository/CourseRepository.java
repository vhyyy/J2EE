package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Hỗ trợ Câu 1: Phân trang danh sách học phần
    Page<Course> findAll(Pageable pageable);
    
    // Hỗ trợ Câu 8: Tìm kiếm học phần theo tên (không phân biệt chữ hoa/thường)
    List<Course> findByNameContainingIgnoreCase(String name);
}