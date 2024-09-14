package com.fiap.tech.restaurante.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private int number;
    private String neighborhood;
    private String city;
    private String state;
    private String type;
    private LocalTime openAt;
    private LocalTime closeAt;
    private LocalTime intervalStart;
    private LocalTime intervalFinish;
    private int numberOfSeats;
    private LocalDateTime createdAt;
}
