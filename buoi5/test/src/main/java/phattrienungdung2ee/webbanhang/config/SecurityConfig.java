package phattrienungdung2ee.webbanhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import phattrienungdung2ee.webbanhang.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AccountService accountService;

    // 1. Khai báo bộ mã hóa mật khẩu BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình AuthenticationProvider để kết nối AccountService và PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(accountService); 
        authProvider.setPasswordEncoder(passwordEncoder()); 
        return authProvider;
    }

    // 3. Quản lý xác thực
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 4. Cấu hình các luật phân quyền (HttpSecurity)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Cho phép truy cập tài nguyên tĩnh và đường dẫn trang đăng nhập tự làm
                .requestMatchers("/css/**", "/js/**", "/images/**", "/upload-dir/**", "/login").permitAll()
                
                // USER và ADMIN đều xem được danh sách sản phẩm
                .requestMatchers("/products").hasAnyRole("USER", "ADMIN")
                
                // Chỉ ADMIN mới được thêm, sửa, xóa
                .requestMatchers("/products/**").hasRole("ADMIN")
                
                // Các trang còn lại phải đăng nhập
                .anyRequest().authenticated()
            )
            // Cấu hình sử dụng form đăng nhập tự làm (login.html)
            .formLogin(form -> form
                .loginPage("/login")               // Trỏ tới đường dẫn hiển thị giao diện của bạn
                .loginProcessingUrl("/login")      // Đường dẫn xử lý dữ liệu khi bấm nút "Đăng Nhập"
                .defaultSuccessUrl("/products", true) // Thành công thì chuyển hướng về /products
                .permitAll()
            )
            // Cấu hình đăng xuất
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // Đăng xuất thành công thì quay lại login và kèm tham số logout
                .permitAll()
            );

        return http.build();
    }
}