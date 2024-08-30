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
import java.util.List;
import java.util.stream.Collectors;

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

            // Check if the user has already reserved this book
            boolean alreadyReserved = reservationRepository.existsByUserAndBook(user, book);
            if (alreadyReserved) {
                throw new RuntimeException("You have already reserved this book: " + book.getTitle());
            }

            if (book.getNb_of_books() <= 0) {
                throw new RuntimeException("No more copies of the book are available for reservation");
            }

            Reservation reservation = Reservation.builder()
                    .user(user)
                    .book(book)
                    .date_intake(LocalDate.now())
                    .date_take_back(LocalDate.now().plusDays(14)) // Example: 2 weeks loan
                    .build();

            reservationRepository.save(reservation);
            book.setNb_of_books(book.getNb_of_books() - 1);
            bookRepository.save(book);
        }
    }
    public String returnBooks(){
        List<Integer> reservationIds = findAll();
        boolean check = false;
        for (Integer reservationId : reservationIds){
            //find the reservation by ID
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(()->new RuntimeException("Reservation not found"));
            if(reservation.getDate_take_back().isAfter(LocalDate.now())){
                //Get the book associated to the reservation ID
                Book book = reservation.getBook();

                //Change the stock quantity of books since they are returned
                book.setNb_of_books(book.getNb_of_books()+1);
                bookRepository.save(book);

                //Delete the reservation after taking the books back to the stock
                reservationRepository.delete(reservation);

                //The message
                check=true;
            }
        }
        if (check){
            return "Books returned successfully";
        }
        return "No books to return";
    }
    public List<Integer> findAll(){
        List<Reservation>reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
    }
}
