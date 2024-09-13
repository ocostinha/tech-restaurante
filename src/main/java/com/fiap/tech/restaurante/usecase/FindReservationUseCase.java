package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.ReservationNotFoundException;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindReservationUseCase {

    private final ReservationRepository repository;

    public Reservation findReservation(UUID id) {
        validateReservationId(id);
        return getReservationById(id);
    }

    private void validateReservationId(UUID id) {
        if (id == null) {
            throw new UnprocessableEntityException("O id da reserva não pode ser nulo");
        }
    }

    private Reservation getReservationById(UUID id) {
        Reservation reservation = repository.findReservationById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException("A reserva não foi encontrada com o id: " + id);
        }
        return reservation;
    }
}
