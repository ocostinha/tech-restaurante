package com.fiap.tech.restaurante.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReservationRequestDto {

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

    @Min(0)
    @Max(23)
    private int reservationHour;

    @Min(1)
    private int numberOfSeats;
}
