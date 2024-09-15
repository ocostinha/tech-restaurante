package com.fiap.tech.restaurante.infra.repository;


import com.fiap.tech.restaurante.infra.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    Optional<SeatEntity> findByIdRestaurant(Long idRestaurant);
}

