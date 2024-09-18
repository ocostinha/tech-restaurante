package com.fiap.tech.restaurante.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
    @JsonFormat(pattern = "HH:mm")
    public String openAt;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    public String closeAt;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    public String intervalStart;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    public String intervalFinish;

    @NotNull
    public int numberOfSeats;
}

