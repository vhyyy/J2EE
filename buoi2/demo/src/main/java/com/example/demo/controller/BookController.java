package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. Hiển thị danh sách sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books"; // Trả về file books.html
    }

    // 2. Hiển thị form thêm sách mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // Trả về file add-book.html
    }

    // 3. Xử lý thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books"; // Chuyển hướng về danh sách
    }

    // 4. Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "edit-book"; // Trả về file edit-book.html
        }
        return "redirect:/books";
    }

    // 5. Xử lý cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookService.updateBook(book.getId(), book);
        return "redirect:/books";
    }

    // 6. Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}