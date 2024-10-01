package com.fiap.tech.restaurante.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Available {

	private Long id;

	private Long idRestaurant;

	private LocalDate date;

	private LocalTime hour;

	private int availableSeats;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
