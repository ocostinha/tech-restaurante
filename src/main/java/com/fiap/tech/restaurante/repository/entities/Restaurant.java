package com.fiap.tech.restaurante.repository.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
