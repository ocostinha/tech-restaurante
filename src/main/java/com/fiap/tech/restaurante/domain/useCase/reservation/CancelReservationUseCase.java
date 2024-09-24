package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelReservationUseCase {

	private final ReservationRepository reservationRepository;

	private final UpdateAvailableUseCase updateAvailableUseCase;

	private final ReservationMapper mapper;

	public Reservation execute(Long reservationId) {
		ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

		reservationEntity.setStatus(ReservationStatus.CANCELED);
		Reservation updatedReservation = mapper.toDomain(reservationRepository.save(reservationEntity));

		updateAvailableUseCase.execute(reservationEntity.getIdRestaurant(), reservationEntity.getSeatsReserved(),
				reservationEntity.getReservationDate(), reservationEntity.getReservationHour());

		return updatedReservation;
	}

}
