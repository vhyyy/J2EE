package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Tìm danh sách môn học đã đăng ký dựa vào đối tượng Sinh viên
    List<Enrollment> findByStudent(Student student);
}