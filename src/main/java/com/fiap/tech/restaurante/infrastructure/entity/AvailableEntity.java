package com.fiap.tech.restaurante.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "available")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableEntity {

    @Id
    private UUID id;

    @Column(name = "id_restaurant", nullable = false)
    private UUID restaurantId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "hour", nullable = false)
    private int hour;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
