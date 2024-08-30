package com.esprit.bookservice.services;

import com.esprit.bookservice.model.Book;
import com.esprit.bookservice.repository.BooksRepository;
import com.esprit.bookservice.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public Book saveBook(Book book){
        return booksRepository.save(book);
    }

    public List<Map<String, Object>> findAll() {
        List<Book> bookList = booksRepository.findAll();
        return bookList.stream()
                .map(book -> {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("id", book.getId());
                    bookMap.put("title", book.getTitle());
                    bookMap.put("author", book.getAuthor());
                    bookMap.put("nb_of_books", book.getNb_of_books());
                    bookMap.put("reservationsCount", book.getReservations().size());
                    return bookMap;
                })
                .collect(Collectors.toList());
    }

    public void deleteBook(Book book){
        boolean isBookReserved = reservationRepository.existsById(book.getId());
        if(isBookReserved){
            throw new IllegalStateException("Cannot delete book as it is currently reserved.");
        }else {
            booksRepository.delete(book);
        }
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
