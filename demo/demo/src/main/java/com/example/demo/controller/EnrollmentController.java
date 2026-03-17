package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    // CÂU 6: Xử lý Đăng ký học phần
    @GetMapping("/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long courseId, Authentication authentication) {
        // Chỉ xử lý nếu user đã đăng nhập
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Student student = studentRepository.findByUsername(username);
            Course course = courseService.getCourseById(courseId);

            if (student != null && course != null) {
                enrollmentService.enrollStudent(student, course);
            }
        }
        return "redirect:/my-courses";
    }

    // CÂU 7: Trang My Courses
    @GetMapping("/my-courses")
    public String myCourses(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username);
        
        // Trả danh sách môn đã đăng ký ra giao diện
        model.addAttribute("enrollments", enrollmentService.getStudentEnrollments(student));
        
        // Truyền các biến cho Navbar thông minh
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("username", username);
        boolean isStudent = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        model.addAttribute("isStudent", isStudent);
        model.addAttribute("isAdmin", false); // Trong trang này ko cần hiện panel admin

        return "my-courses";
    }
}