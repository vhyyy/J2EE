package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.Student;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login"; // Sẽ trỏ tới file login.html
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register"; // Sẽ trỏ tới file register.html
    }

    // Xử lý dữ liệu khi bấm nút Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("student") Student student) {
        // Mã hóa mật khẩu
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        
        // Gán quyền mặc định là STUDENT theo Câu 3
        Role userRole = roleRepository.findByName("ROLE_STUDENT");
        student.setRoles(Collections.singleton(userRole));
        
        // Lưu xuống Database
        studentRepository.save(student);
        
        return "redirect:/login?success";
    }
}