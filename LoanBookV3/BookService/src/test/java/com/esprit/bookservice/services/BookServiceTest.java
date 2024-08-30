package com.esprit.bookservice.services;

import com.esprit.bookservice.model.Book;
import com.esprit.bookservice.repository.BooksRepository;
import com.esprit.bookservice.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BooksRepository booksRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        //Nothing here
    }

    @Test
    void saveBook() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");

        when(booksRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals(1, savedBook.getId());
        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void findAll() {
        Book book1 = new Book(3, "Title1", "Author1", 5,new ArrayList<>(), 0);
        Book book2 = new Book(4, "Title2", "Author2", 3,new ArrayList<>(), 0);

        when(booksRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Map<String, Object>> books = bookService.findAll();
        // Then
        assertEquals(2, books.size());

        // Check first book
        assertEquals(3, books.get(0).get("id"));
        assertEquals("Title1", books.get(0).get("title"));
        assertEquals("Author1", books.get(0).get("author"));
        assertEquals(5, books.get(0).get("nb_of_books"));
        assertEquals(0, books.get(0).get("reservationsCount")); // Assuming no reservations

        // Check second book
        assertEquals(4, books.get(1).get("id"));
        assertEquals("Title2", books.get(1).get("title"));
        assertEquals("Author2", books.get(1).get("author"));
        assertEquals(3, books.get(1).get("nb_of_books"));
        assertEquals(0, books.get(1).get("reservationsCount")); // Assuming no reservations

        verify(booksRepository, times(1)).findAll();
    }

    @Test
    void deleteBook_WhenBookIsNotReserved() {
        Book book = new Book(1, "Title", "Author", 5,new ArrayList<>(), 0);

        when(reservationRepository.existsById(book.getId())).thenReturn(false);

        assertDoesNotThrow(() -> bookService.deleteBook(book));
        verify(booksRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_WhenBookIsReserved() {
        Book book = new Book(1, "Title", "Author", 5,new ArrayList<>(), 0);

        when(reservationRepository.existsById(book.getId())).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> bookService.deleteBook(book));

        assertEquals("Cannot delete book as it is currently reserved.", exception.getMessage());
        verify(booksRepository, never()).delete(book);
    }

    @Test
    void updateBook_WhenBookExists() {
        Book book = new Book(1, "Title", "Author", 5,new ArrayList<>(), 0);
        Book updatedBook = new Book(1, "New Title", "New Author", 10,new ArrayList<>(), 0);

        when(booksRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> bookService.updateBook(updatedBook));
        verify(booksRepository, times(1)).save(book);
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals(10, book.getNb_of_books());
    }

    @Test
    void updateBook_WhenBookDoesNotExist() {
        Book book = new Book(1, "Title", "Author", 5,new ArrayList<>(), 0);

        when(booksRepository.findById(book.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(book));

        assertEquals("Book not found with title: Title", exception.getMessage());
        verify(booksRepository, never()).save(book);
    }
}