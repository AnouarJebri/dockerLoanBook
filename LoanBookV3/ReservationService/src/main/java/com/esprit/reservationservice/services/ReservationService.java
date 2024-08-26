package com.esprit.reservationservice.services;

import com.esprit.reservationservice.DTO.ReservationRequestDTO;
import com.esprit.reservationservice.Repositories.BookRepository;
import com.esprit.reservationservice.Repositories.ReservationRepository;
import com.esprit.reservationservice.Repositories.UserRepository;
import com.esprit.reservationservice.model.Book;
import com.esprit.reservationservice.model.Reservation;
import com.esprit.reservationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public void createReservations(ReservationRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (Integer bookId : requestDTO.getBookIds()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            Reservation reservation = Reservation.builder()
                    .user(user)
                    .book(book)
                    .date_intake(LocalDate.now())
                    .date_take_back(LocalDate.now().plusDays(14)) // Example: 2 weeks loan
                    .build();

            reservationRepository.save(reservation);
        }
    }
}
