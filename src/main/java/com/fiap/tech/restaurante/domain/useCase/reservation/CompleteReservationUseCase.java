package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompleteReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;

    public Reservation execute(Long id) {
        ReservationEntity reservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Reserva não encontrada"));

        reservationEntity.setStatus(ReservationStatus.COMPLETED);

        return mapper.toDomain(reservationRepository.save(reservationEntity));
    }
}
