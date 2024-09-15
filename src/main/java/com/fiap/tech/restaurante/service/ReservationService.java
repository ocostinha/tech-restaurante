package com.fiap.tech.restaurante.service;

import com.fiap.tech.restaurante.model.Reservation;
import com.fiap.tech.restaurante.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Optional<Reservation> completeReservation(Long id) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus("COMPLETE");
            reservationRepository.save(reservation);
            return reservation;
        });
    }
}
