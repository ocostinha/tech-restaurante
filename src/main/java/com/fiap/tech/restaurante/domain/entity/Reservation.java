package com.fiap.tech.restaurante.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    private UUID id;
    private UUID restaurantId;
    private String reservationOwnerName;
    private String reservationOwnerEmail;
    private LocalDate reservationDate;
    private int reservationHour;
    private int numberOfSeats;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateReservation(String reservationOwnerName, String reservationOwnerEmail, LocalDate reservationDate,
                                  int reservationHour, int numberOfSeats) {
        this.reservationOwnerName = reservationOwnerName;
        this.reservationOwnerEmail = reservationOwnerEmail;
        this.reservationDate = reservationDate;
        this.reservationHour = reservationHour;
        this.numberOfSeats = numberOfSeats;
        this.updatedAt = LocalDateTime.now();
    }


}
