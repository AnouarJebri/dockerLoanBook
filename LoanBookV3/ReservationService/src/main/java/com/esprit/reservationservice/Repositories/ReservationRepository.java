package com.esprit.reservationservice.Repositories;

import com.esprit.reservationservice.model.Book;
import com.esprit.reservationservice.model.Reservation;
import com.esprit.reservationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByUserAndBook(User user, Book book);
}
