package com.fiap.tech.restaurante.infra.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "id_restaurant", nullable = false)
    private String location;
    private String cuisineType;
    private int availableSeats;
}
