package com.esprit.reservationservice.services;

import com.esprit.reservationservice.DTO.ReservationRequestDTO;
import com.esprit.reservationservice.Repositories.BookRepository;
import com.esprit.reservationservice.Repositories.ReservationRepository;
import com.esprit.reservationservice.Repositories.UserRepository;
import com.esprit.reservationservice.model.Book;
import com.esprit.reservationservice.model.Reservation;
import com.esprit.reservationservice.model.User;
import com.esprit.reservationservice.model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@DataJpaTest
//@Transactional
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private User user;
    private Book book;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        user = new User();
        user.setId(5);
        user.setNom("test");
        user.setPrenom("user");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setRole(UserRole.SUBSCRIBER);

        book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setNb_of_books(5);

        reservation = Reservation.builder()
                .id(1)
                .user(user)
                .book(book)
                .date_intake(LocalDate.now())
                .date_take_back(LocalDate.now().plusDays(14))
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createReservations() {
        // Given
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setUserId(user.getId());
        requestDTO.setBookIds(Collections.singletonList(book.getId()));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(reservationRepository.existsByUserAndBook(user, book)).thenReturn(false);

        // When
        reservationService.createReservations(requestDTO);

        // Then
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createReservations_WhenBookAlreadyReserved_ShouldThrowException() {
        // Given
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setUserId(user.getId());
        requestDTO.setBookIds(Collections.singletonList(book.getId()));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(reservationRepository.existsByUserAndBook(user, book)).thenReturn(true);

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> reservationService.createReservations(requestDTO));
        assertEquals("You have already reserved this book: Test Book", thrown.getMessage());
    }

    @Test
    void returnBooks() {
        // Given
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservation));
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        // When
        String result = reservationService.returnBooks();

        // Then
        assertEquals("Books returned successfully", result);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(reservationRepository, times(1)).delete(any(Reservation.class));
    }

    @Test
    void returnBooks_WhenNoReservations_ShouldReturnNoBooksToReturn() {
        // Given
        when(reservationRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        String result = reservationService.returnBooks();

        // Then
        assertEquals("No books to return", result);
    }

    @Test
    void findAll() {
        // Given
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservation));

        // When
        List<Integer> reservationIds = reservationService.findAll();

        // Then
        assertEquals(Collections.singletonList(reservation.getId()), reservationIds);
    }
}