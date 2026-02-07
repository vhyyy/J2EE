package com.example.validation.controller;

import com.example.validation.model.Category;
import com.example.validation.model.Product;
import com.example.validation.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*; // Import đầy đủ
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Danh sách danh mục cố định
    private List<Category> categories = Arrays.asList(
        new Category(1, "Laptop"),
        new Category(2, "Điện thoại"),
        new Category(3, "Máy tính bảng"),
        new Category(4, "Tai nghe"),
        new Category(5, "Máy ảnh")
    );

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listProduct", productService.getAll());
        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories); 
        return "products/create";
    }

    @PostMapping("/create")
    public String save(@Valid Product product, BindingResult result, 
                       @RequestParam("imageProduct") MultipartFile imageProduct, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categories);
            return "products/create";
        }
        productService.add(product, imageProduct);
        return "redirect:/products";
    }

    // ---- PHẦN MỚI THÊM: EDIT & DELETE ----

    // 1. Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) return "redirect:/products"; // Không tìm thấy thì quay về danh sách
        
        model.addAttribute("product", product);
        model.addAttribute("categories", categories); // Gửi danh mục sang để hiện dropdown
        return "products/edit";
    }

    // 2. Xử lý cập nhật
    @PostMapping("/edit")
    public String edit(@Valid Product product, BindingResult result, 
                       @RequestParam("imageProduct") MultipartFile imageProduct, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categories);
            return "products/edit"; // Nếu lỗi thì ở lại trang sửa
        }
        productService.edit(product, imageProduct);
        return "redirect:/products";
    }

    // 3. Xử lý xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}