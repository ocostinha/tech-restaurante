package com.fiap.tech.restaurante.application.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestaurantResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
}

