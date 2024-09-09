package com.fiap.tech.restaurante.domain.repository;

import com.fiap.tech.restaurante.domain.entity.Available;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailableRepository {
    Optional<Available> findAvailability(UUID restaurantId, LocalDate date, int hour);
    void save(Available available);
    List<Available> findAll();
    List<Available> findBySeatsAndHour(int seats, int hour);

    List<Available> findBySeats(Integer seats);

    List<Available> findByHour(Integer hour);
}
