package com.fiap.tech.restaurante.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Available {

	private Long id;

	private Long idRestaurant;

	private LocalDate date;

	private LocalTime hour;

	private int availableSeats;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
