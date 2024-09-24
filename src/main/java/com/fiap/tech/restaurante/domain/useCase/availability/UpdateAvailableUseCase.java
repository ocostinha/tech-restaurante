package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UpdateAvailableUseCase {

	private final AvailableRepository availableRepository;

	public void execute(Long idRestaurant, Integer seatsToAdd, LocalDate reservationDate, LocalTime reservationTime) {
		AvailableEntity seatEntity = availableRepository
			.findByIdRestaurantAndDateAndHour(idRestaurant, reservationDate, reservationTime)
			.orElseThrow(() -> new ResourceNotFoundException("Available not found"));

		seatEntity.setAvailableSeats(seatEntity.getAvailableSeats() + seatsToAdd);

		availableRepository.save(seatEntity);
	}

}
