package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Student;
import com.example.demo.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enrollStudent(Student student, Course course) {
        // Kiểm tra xem sinh viên đã đăng ký môn này chưa
        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudent(student);
        boolean alreadyEnrolled = existingEnrollments.stream()
                .anyMatch(e -> e.getCourse().getId().equals(course.getId()));

        if (!alreadyEnrolled) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentRepository.save(enrollment);
        }
    }

    public List<Enrollment> getStudentEnrollments(Student student) {
        return enrollmentRepository.findByStudent(student);
    }
}