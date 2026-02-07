package com.example.demo.model;

public class Book {
    private int id;
    private String title;
    private String author;

    // Constructor rỗng (Bắt buộc phải có)
    public Book() {}

    // Constructor đầy đủ
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // --- GETTER & SETTER (Bắt buộc để BookService không báo lỗi) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}