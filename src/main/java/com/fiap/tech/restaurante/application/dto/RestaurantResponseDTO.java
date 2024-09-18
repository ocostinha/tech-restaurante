package com.fiap.tech.restaurante.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "HH:mm")
    private LocalTime openAt;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeAt;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime intervalStart;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime intervalFinish;

    private int numberOfSeats;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
