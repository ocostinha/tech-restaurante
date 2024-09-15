package com.fiap.tech.restaurante.infra.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "cuisine_type", nullable = false)
    private String cuisineType;

    @Column(name = "openAt", nullable = false)
    private LocalTime openAt;

    @Column(name = "closeAt", nullable = false)
    private LocalTime closeAt;

    @Column(name = "intervalStart", nullable = false)
    private LocalTime intervalStart;

    @Column(name = "intervalFinish", nullable = false)
    private LocalTime intervalFinish;

    @Column(name = "numberOfSeats", nullable = false)
    private int numberOfSeats;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
