package com.example.demo.controller;

import org.springframework.stereotype.Controller; // Chú ý: dùng @Controller, KHÔNG dùng @RestController
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HomeController {

    // Khi người dùng vào đường dẫn /home
    @GetMapping("/home") 
    public String index() {
        return "index"; // Trả về tên file "index" (nó sẽ tự tìm file index.html trong thư mục templates)
    }
}