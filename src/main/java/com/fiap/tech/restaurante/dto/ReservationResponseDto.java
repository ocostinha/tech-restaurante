package com.fiap.tech.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReservationResponseDto {
    private UUID reservationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

    public ReservationResponseDto(String message) {
        this.message = message;
    }
}

