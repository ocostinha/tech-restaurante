package com.fiap.tech.restaurante.repository;

import com.fiap.tech.restaurante.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
