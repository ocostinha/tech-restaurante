package com.fiap.tech.restaurante.domain.useCase.reservation;


import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.seats.UpdateAvailableSeatUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CancelReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final UpdateAvailableSeatUseCase updateAvailableSeatUseCase;
    private final ReservationMapper mapper;

    public Reservation execute(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservationEntity.setStatus(ReservationStatus.CANCELED);
        reservationEntity.setUpdatedAt(LocalDateTime.now());
        Reservation updatedReservation = mapper.toDomain(reservationRepository.save(reservationEntity));

        updateAvailableSeatUseCase.execute(reservationEntity.getIdRestaurant(), reservationEntity.getSeatsReserved());

        return updatedReservation;
    }
}
