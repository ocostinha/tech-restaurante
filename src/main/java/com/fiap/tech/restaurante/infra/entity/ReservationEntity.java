package com.fiap.tech.restaurante.infra.entity;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "id_restaurant", nullable = false)
    private Long idRestaurant;

    @Column(name = "reservation_owner_name", nullable = false)
    private String reservationOwnerName;

    @Column(name = "reservation_owner_email", nullable = false)
    private String reservationOwnerEmail;

    @Column(name = "reservation_data", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_hour", nullable = false)
    private LocalTime reservationHour;

    @Column(name = "seats_reserved", nullable = false)
    private Integer seatsReserved;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
