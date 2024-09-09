package com.fiap.tech.restaurante.domain.repository;

import com.fiap.tech.restaurante.domain.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Optional<Reservation> findById(UUID id);

    void save(Reservation reservation);
}
