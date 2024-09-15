package com.fiap.tech.restaurante.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ReservationRequestDTO {

    @NotNull
    private UUID idRestaurant;

    @NotBlank
    @Size(max = 120)
    private String reservationOwnerName;

    @Email
    @NotBlank
    @Size(max = 120)
    private String reservationOwnerEmail;

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private LocalTime reservationHour;

    @Min(1)
    private int numberOfSeats;
}
