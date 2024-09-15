package com.fiap.tech.restaurante.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDTO {

    @NotNull
    private Long idRestaurant;

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
    private int seatsReserved;
}
