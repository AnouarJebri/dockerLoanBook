package com.esprit.bookservice.services;

import com.esprit.bookservice.model.Book;
import com.esprit.bookservice.repository.BooksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BooksRepository booksRepository;

    public Book saveBook(Book book){
        return booksRepository.save(book);
    }

    public List<Book> findAll() {
        List<Book> bookList = booksRepository.findAll();
        return bookList.stream()
                .map(book -> new Book(book.getId(),book.getTitle(),book.getAuthor(),book.getNb_of_books()))
                .collect(Collectors.toList());
    }

    public void deleteBook(Book book){
        booksRepository.delete(book);
    }

    public void updateBook(Book book){
        Optional<Book> existingBookOptional = booksRepository.findById(book.getId());
        if(existingBookOptional.isPresent()){
            Book existingBook = existingBookOptional.get();

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setNb_of_books(book.getNb_of_books());

            booksRepository.save(existingBook);
        }
        else {
            throw new EntityNotFoundException("Book not found with title: " + book.getTitle());
        }
    }
}
