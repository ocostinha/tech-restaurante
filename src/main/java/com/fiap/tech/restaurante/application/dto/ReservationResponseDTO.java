package com.fiap.tech.restaurante.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private String status;
    private Integer seatsReserved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
