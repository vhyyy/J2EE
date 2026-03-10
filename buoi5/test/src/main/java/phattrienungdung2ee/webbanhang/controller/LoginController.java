package phattrienungdung2ee.webbanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Ánh xạ đường dẫn /login tới file login.html của bạn
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }
}