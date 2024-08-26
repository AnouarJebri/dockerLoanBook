package com.esprit.reservationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {
    private Integer userId;
    private List<Integer> bookIds;

    @Override
    public String toString() {
        return "ReservationRequestDTO{" +
                "userId=" + userId +
                ", bookIds=" + bookIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequestDTO that = (ReservationRequestDTO) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookIds, that.bookIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookIds);
    }
}
