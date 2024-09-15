package com.fiap.tech.restaurante.addapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class RequestReservationDTO {

    private UUID restaurantId;
    private String reservationOwnerName;
    private String reservationOwnerEmail;
    private LocalDate reservationDate;
    private int reservationHour;
    private int numberOfSeats;

}