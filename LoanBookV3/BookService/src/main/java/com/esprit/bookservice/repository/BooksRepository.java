package com.esprit.bookservice.repository;

import com.esprit.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book,Integer> {
}
