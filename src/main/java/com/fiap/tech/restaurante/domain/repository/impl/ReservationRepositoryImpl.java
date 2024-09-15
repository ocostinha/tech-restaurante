package com.fiap.tech.restaurante.domain.repository.impl;

import com.fiap.tech.restaurante.addapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.infrastructure.repository.JpaReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    @Autowired
    private final JpaReservationRepository reservationRepository;
    @Autowired
    private final ReservationMapper mapper;

    @Override
    public Optional<Reservation> findById(UUID id) {
        return reservationRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(mapper.toEntity(reservation));
    }
}
