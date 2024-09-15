package com.fiap.tech.restaurante.application.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RestaurantRequestDTO {
    @NotNull
    public String name;

    @NotNull
    public String address;

    @NotNull
    public int number;

    @NotNull
    public String neighborhood;

    @NotNull
    public String city;

    @NotNull
    public String state;

    @NotNull
    public String cuisineType;

    @NotNull
    public LocalTime openAt;

    @NotNull
    public LocalTime closeAt;

    @NotNull
    public LocalTime intervalStart;

    @NotNull
    public LocalTime intervalFinish;

    @NotNull
    public int numberOfSeats;
}

