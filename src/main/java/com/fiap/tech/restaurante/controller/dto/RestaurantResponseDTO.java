package com.fiap.tech.restaurante.controller.dto;
import java.time.LocalDateTime;

public class RestaurantResponseDTO {
    private Long id;
    private LocalDateTime createdAt;

    public RestaurantResponseDTO(Long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}

