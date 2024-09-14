package com.fiap.tech.restaurante.controller.dto;
import java.time.LocalTime;

public class RestaurantRequestDTO {
    public String name;
    public String address;
    public int number;
    public String neighborhood;
    public String city;
    public String state;
    public String type;
    public LocalTime openAt;
    public LocalTime closeAt;
    public LocalTime intervalStart;
    public LocalTime intervalFinish;
    public int numberOfSeats;

    // Getters and Setters
}

