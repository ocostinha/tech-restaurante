package com.fiap.tech.restaurante.domain.repository;

import com.fiap.tech.restaurante.domain.entity.Reservation;

import java.util.UUID;

public interface ReservationRepository {
    Reservation     findReservationById(UUID id);
}
