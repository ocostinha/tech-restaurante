package com.fiap.tech.restaurante.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableResponseDTO {
    private Long idRestaurant;
    private LocalDate date;
    private LocalTime hour;
    private int availableSeats;
}
