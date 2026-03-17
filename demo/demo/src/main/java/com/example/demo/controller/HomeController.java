package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @GetMapping({"/", "/home", "/courses"})
    public String home(@RequestParam(defaultValue = "0") int page, 
                       @RequestParam(required = false) String keyword, 
                       Model model, Authentication authentication) {
        
        // CÂU 8: Xử lý tìm kiếm nếu có keyword
        if (keyword != null && !keyword.isEmpty()) {
            List<Course> searchResults = courseService.searchCourses(keyword);
            model.addAttribute("courses", searchResults);
            model.addAttribute("totalPages", 0); // Ẩn phân trang khi đang tìm kiếm
            model.addAttribute("keyword", keyword);
        } else {
            // Hiển thị phân trang 5 học phần/trang (Câu 1)
            int pageSize = 5; 
            Page<Course> coursePage = courseService.getPaginatedCourses(page, pageSize);
            model.addAttribute("courses", coursePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", coursePage.getTotalPages());
        }
        
        // Truyền thông tin đăng nhập và quyền (Role) ra giao diện
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", authentication.getName());
            
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);

            boolean isStudent = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
            model.addAttribute("isStudent", isStudent);
        } else {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isStudent", false);
        }
        
        return "home"; 
    }
}