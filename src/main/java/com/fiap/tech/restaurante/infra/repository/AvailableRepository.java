package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailableRepository extends JpaRepository<AvailableEntity, UUID> {
    Optional<AvailableEntity> findByIdRestaurantAndDateAndHour(Long restaurantId, LocalDate date, LocalTime hour);

    @Query("from AvailableEntity where availableSeats >= :seats and hour = :hour")
    List<AvailableEntity> findByAvailableSeatsAndHour(int seats, LocalTime hour);

    List<AvailableEntity> findByAvailableSeatsGreaterThanEqual(int seats);

    List<AvailableEntity> findByHour(LocalTime hour);

    Optional<AvailableEntity> findByIdRestaurant(Long idRestaurant);
}
