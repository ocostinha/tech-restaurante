package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.ReservationNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindReservationUseCase {

    private  final ReservationRepository repository;

    public Reservation findReservation(UUID id) throws IllegalArgumentException, ReservationNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("O id da reserva não pode ser nulo");
        }

        Reservation reservation = repository.findReservationById(id);
if (reservation == null){
            throw new ReservationNotFoundException("A reserva não foi encontrada com o id: " + id);
        }

        return reservation;
    }
}
