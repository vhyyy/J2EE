package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    
    private List<Book> books = new ArrayList<>();

    // Constructor: Tạo sẵn vài cuốn sách mẫu
    public BookService() {
        books.add(new Book(1, "Lap Trinh Java", "Nguyen Van A"));
        books.add(new Book(2, "Spring Boot Co Ban", "Tran Van B"));
    }

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // --- PHẦN QUAN TRỌNG: Logic tự động tăng ID ---
    public void addBook(Book book) {
        // Tìm số ID lớn nhất hiện có
        int maxId = books.stream()
                         .mapToInt(Book::getId)
                         .max()
                         .orElse(0);
        
        // Gán ID mới = ID lớn nhất + 1
        book.setId(maxId + 1);
        
        // Lưu vào danh sách
        books.add(book);
    }

    // Cập nhật sách
    public void updateBook(int id, Book bookMoi) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(bookMoi.getTitle());
                book.setAuthor(bookMoi.getAuthor());
                break;
            }
        }
    }

    // Xóa sách
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}