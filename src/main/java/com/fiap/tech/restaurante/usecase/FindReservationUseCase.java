package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.ReservationNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindReservationUseCase {

    private final ReservationRepository reservationRepository;

    public Reservation findReservation(Long id) throws IllegalArgumentException {

        Reservation reservation = reservationRepository.findById(id);

        if (id == null) {
            throw new IllegalArgumentException("O id da reserva não pode ser nulo");
        }

        if (reservation == null) {
            throw new ReservationNotFoundException("A reserva não foi encontrada com o id: " + id);
        }

        return reservation;
    }
}
