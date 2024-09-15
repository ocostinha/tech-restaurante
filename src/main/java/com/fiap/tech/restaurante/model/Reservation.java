package com.fiap.tech.restaurante.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String reservationOwnerName;
    private String reservationOwnerEmail;
    private LocalDate reservationDate;
    private int reservationHour;
    private int numberOfSeats;
    private String status;

    @ManyToOne
    private Restaurant restaurant;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
