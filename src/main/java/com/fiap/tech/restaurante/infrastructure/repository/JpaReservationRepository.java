package com.fiap.tech.restaurante.infrastructure.repository;

import com.fiap.tech.restaurante.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaReservationRepository extends JpaRepository<ReservationEntity, UUID> {
}
