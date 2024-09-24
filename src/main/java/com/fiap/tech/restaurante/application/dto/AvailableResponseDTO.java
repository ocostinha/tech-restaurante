package com.fiap.tech.restaurante.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableResponseDTO {

	private Long idRestaurant;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime hour;

	private int availableSeats;

}
