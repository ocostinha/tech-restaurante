package com.fiap.tech.restaurante.domain.model;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class Reservation {
    private Long id;
    private Long idRestaurant;
    private String reservationOwnerName;
    private String reservationOwnerEmail;
    private LocalDate reservationDate;
    private LocalTime reservationHour;
    private Integer seatsReserved;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
