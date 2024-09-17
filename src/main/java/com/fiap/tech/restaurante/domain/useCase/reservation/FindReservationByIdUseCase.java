package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindReservationByIdUseCase {

    private final ReservationRepository repository;
    private final ReservationMapper mapper;

    public Reservation execute(Long id) {
        validateReservationId(id);
        return mapper.toDomain(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"))
        );
    }

    private void validateReservationId(Long id) {
        if (id == null) {
            throw new UnprocessableEntityException("O id da reserva não pode ser nulo");
        }
    }
}
