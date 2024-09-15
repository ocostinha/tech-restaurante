package com.fiap.tech.restaurante.addapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDTO {
    private UUID restaurantId;
    private LocalDate date;
    private int hour;
    private int availableSeats;
}
