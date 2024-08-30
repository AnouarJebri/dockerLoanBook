package com.esprit.bookservice.controller;

import com.esprit.bookservice.model.Book;
import com.esprit.bookservice.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    @PostMapping("/BookAdd")
    public String addBook(@RequestBody Book book){
        bookService.saveBook(book);
        return "book saved successfully";
    }

    @PostMapping("/DeleteBook")
    public String bookDelete(@RequestBody Book book){
        bookService.deleteBook(book);
        return "Book deleted successfully";
    }

    @PostMapping("/UpdateBook")
    public String updateBooks(@RequestBody Book book){
        bookService.updateBook(book);
        return "Book updated successfully";
    }

    @GetMapping("/ListBook")
    public ResponseEntity<List<Map<String, Object>>> getAllBooks() {
        List<Map<String, Object>> bookList = bookService.findAll();
        return ResponseEntity.ok(bookList);
    }
}
