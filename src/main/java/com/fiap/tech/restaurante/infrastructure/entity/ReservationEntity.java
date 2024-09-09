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
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationEntity {

    @Id
    private UUID id;

    @Column(name = "id_restaurant", nullable = false)
    private UUID restaurantId;

    @Column(name = "reservation_owner_name", nullable = false, length = 120)
    private String reservationOwnerName;

    @Column(name = "reservation_owner_email", nullable = false, length = 120)
    private String reservationOwnerEmail;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_hour", nullable = false)
    private int reservationHour;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
