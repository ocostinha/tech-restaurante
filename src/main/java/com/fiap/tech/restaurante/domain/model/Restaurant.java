package com.fiap.tech.restaurante.domain.model;

import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Restaurant {

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
