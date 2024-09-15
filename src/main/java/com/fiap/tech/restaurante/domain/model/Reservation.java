package com.fiap.tech.restaurante.domain.model;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Reservation {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ReservationStatus status;
    private Long idRestaurant;
    private Integer seatsReserved;
}
