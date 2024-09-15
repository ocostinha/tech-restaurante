package com.fiap.tech.restaurante.infrastructure.repository;

import com.fiap.tech.restaurante.infrastructure.entity.AvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAvailableRepository extends JpaRepository<AvailableEntity, UUID> {
    Optional<AvailableEntity> findByRestaurantIdAndDateAndHour(UUID restaurantId, LocalDate date, int hour);

    @Query("from AvailableEntity where availableSeats >= :seats and hour = :hour")
    List<AvailableEntity> findByAvailableSeatsAndHour(int seats, int hour);

    List<AvailableEntity> findByAvailableSeatsGreaterThanEqual(int seats);

    List<AvailableEntity> findByHour(int hour);
}
