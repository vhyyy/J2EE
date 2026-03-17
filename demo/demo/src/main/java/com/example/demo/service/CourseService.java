package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Phân trang cho Câu 1
    public Page<Course> getPaginatedCourses(int page, int size) {
        return courseRepository.findAll(PageRequest.of(page, size));
    }

    // CÂU 8: Tìm kiếm học phần theo tên
    public List<Course> searchCourses(String keyword) {
        return courseRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Các hàm phục vụ CRUD
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }
}