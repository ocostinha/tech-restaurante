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
public class Available {

    private UUID id;
    private UUID restaurantId;
    private LocalDate date;
    private int hour;
    private int availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void subtractSeats(int seats) {
        this.availableSeats -= seats;
        this.updatedAt = LocalDateTime.now();
    }

}
