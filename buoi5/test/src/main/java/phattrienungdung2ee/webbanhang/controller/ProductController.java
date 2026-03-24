package phattrienungdung2ee.webbanhang.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdung2ee.webbanhang.model.Category;
import phattrienungdung2ee.webbanhang.model.Product;
import phattrienungdung2ee.webbanhang.repository.ProductRepository;
import phattrienungdung2ee.webbanhang.service.CategoryService;
import phattrienungdung2ee.webbanhang.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Thêm Repository để dùng hàm searchAndFilter (Câu 1, 2, 3, 4)
    @Autowired
    private ProductRepository productRepository;

    // ĐÃ CẬP NHẬT: Hàm hiển thị danh sách tích hợp Tìm kiếm, Lọc, Sắp xếp và Phân trang
    @GetMapping
    public String index(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "sort", defaultValue = "default") String sort,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        // --- CÂU 3: Xử lý Sắp xếp ---
        Sort sortable = Sort.by("id").descending(); 
        if ("priceAsc".equals(sort)) {
            sortable = Sort.by("price").ascending();
        } else if ("priceDesc".equals(sort)) {
            sortable = Sort.by("price").descending();
        }

        // --- CÂU 2: Xử lý Phân trang (5 sản phẩm / 1 trang) ---
        Pageable pageable = PageRequest.of(page - 1, 5, sortable);

        // --- CÂU 1 & CÂU 4: Gọi DB để Tìm kiếm và Lọc ---
        Page<Product> productPage = productRepository.searchAndFilter(keyword, categoryId, pageable);

        // Đẩy dữ liệu ra ngoài Giao diện
        model.addAttribute("listproduct", productPage.getContent());
        model.addAttribute("categories", categoryService.getAll());
        
        // Đẩy các tham số trạng thái ra ngoài để HTML giữ được lựa chọn khi lật trang
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);

        return "product/products"; // Giữ đúng đường dẫn file HTML cũ của bạn
    }

    // ==========================================
    // CÁC HÀM BÊN DƯỚI ĐƯỢC GIỮ NGUYÊN 100%
    // ==========================================

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct,
                         BindingResult result,
                         @RequestParam(value = "category.id", required = false) Integer categoryId,
                         @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                         Model model) {
        if (categoryId == null || categoryId <= 0) {
            result.rejectValue("category", "category.required", "Vui lòng chọn danh mục");
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        try {
            if (imageProduct != null && !imageProduct.isEmpty()) {
                productService.updateImage(newProduct, imageProduct);
            }
            Category selectedCategory = categoryService.get(categoryId);
            newProduct.setCategory(selectedCategory);
            productService.add(newProduct);
            return "redirect:/products";
        } catch (Exception e) {
            result.reject("error.general", "Lỗi: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") Product editProduct,
                       BindingResult result,
                       @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        try {
            if (editProduct.getCategory() != null && editProduct.getCategory().getId() != null && editProduct.getCategory().getId() > 0) {
                Category cat = categoryService.get(editProduct.getCategory().getId());
                editProduct.setCategory(cat);
            }
            if (imageProduct != null && !imageProduct.isEmpty()) {
                productService.updateImage(editProduct, imageProduct);
            }
            productService.update(editProduct);
            return "redirect:/products";
        } catch (Exception e) {
            result.reject("error.general", "Lỗi: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}