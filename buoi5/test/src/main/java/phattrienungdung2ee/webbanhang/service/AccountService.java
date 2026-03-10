package phattrienungdung2ee.webbanhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phattrienungdung2ee.webbanhang.model.Account;
import phattrienungdung2ee.webbanhang.repository.AccountRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));

        Set<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // --- BẮT ĐẦU ĐOẠN KIỂM TRA ---
        System.out.println("=== THÔNG TIN TÀI KHOẢN TỪ DB ===");
        System.out.println("1. Tên đăng nhập: " + account.getLogin_name());
        System.out.println("2. Quyền (Roles): " + authorities); 
        
        // Tự động mã hóa chữ "123" bằng BCrypt chuẩn nhất ngay tại lúc chạy
        String pass123 = new BCryptPasswordEncoder().encode("123");
        // --- KẾT THÚC ĐOẠN KIỂM TRA ---

        return new org.springframework.security.core.userdetails.User(
                account.getLogin_name(),
                pass123, // Cố tình bỏ qua mật khẩu DB, dùng luôn pass "123" này
                authorities
        );
    }
}