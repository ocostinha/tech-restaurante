package com.fiap.tech.restaurante.service;


import com.fiap.tech.restaurante.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.model.Reservation;
import com.fiap.tech.restaurante.model.ReservationStatus;
import com.fiap.tech.restaurante.repository.ReservationRepository;
import com.fiap.tech.restaurante.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatService seatService;

    public ReservationResponse cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELED);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);

        seatService.updateAvailableSeats(reservation.getSeatsReserved());

        return ReservationResponse.builder()
                .id(reservation.getId())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }
}
