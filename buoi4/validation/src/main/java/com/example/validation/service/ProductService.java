package com.example.validation.service;

import com.example.validation.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public ProductService() {}

    public List<Product> getAll() {
        return listProduct;
    }

    // 1. Tìm sản phẩm theo ID
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 2. Thêm mới
    public void add(Product newProduct, MultipartFile imageProduct) {
        // Tự động tăng ID
        int newId = listProduct.size() > 0 ? listProduct.get(listProduct.size() - 1).getId() + 1 : 1;
        newProduct.setId(newId);
        
        // Lưu ảnh nếu có
        saveImage(newProduct, imageProduct);
        
        listProduct.add(newProduct);
    }

    // 3. Chỉnh sửa
    public void edit(Product editProduct, MultipartFile imageProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            
            // Chỉ cập nhật ảnh nếu người dùng chọn file mới
            if (imageProduct != null && !imageProduct.isEmpty()) {
                saveImage(find, imageProduct);
            }
        }
    }

    // 4. Xóa
    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }

    // Hàm phụ để lưu ảnh (tránh viết lặp code)
    private void saveImage(Product product, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String newFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                // Lưu vào target/classes để không cần restart server mới thấy ảnh
                Path pathDir = Paths.get("target/classes/static/images"); 
                if (!Files.exists(pathDir)) Files.createDirectories(pathDir);
                
                Path filePath = pathDir.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                product.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}