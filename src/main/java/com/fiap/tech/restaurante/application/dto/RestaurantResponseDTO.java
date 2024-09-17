package com.fiap.tech.restaurante.application.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String address;
    private int number;
    private String neighborhood;
    private String city;
    private String state;
    private String cuisineType;
    private LocalTime openAt;
    private LocalTime closeAt;
    private LocalTime intervalStart;
    private LocalTime intervalFinish;
    private int numberOfSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
