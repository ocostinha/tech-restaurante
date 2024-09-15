package com.fiap.tech.restaurante.domain.repository.impl;

import com.fiap.tech.restaurante.addapter.mapper.AvailableMapper;
import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.domain.repository.AvailableRepository;
import com.fiap.tech.restaurante.infrastructure.repository.JpaAvailableRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AvailableRepositoryImpl implements AvailableRepository {

    @Autowired
    private final JpaAvailableRepository repository;

    @Autowired
    private final AvailableMapper availableMapper;

    @Override
    public Optional<Available> findAvailability(UUID restaurantId, LocalDate date, int hour) {
        return repository.findByRestaurantIdAndDateAndHour(restaurantId, date, hour)
                .map(availableMapper::toDomain);
    }

    @Override
    public void save(Available available) {
        repository.save(availableMapper.toEntity(available));
    }

    @Override
    public List<Available> findAll() {
        return repository.findAll().stream()
                .map(availableMapper ::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Available> findBySeatsAndHour(int seats, int hour) {
        return repository.findByAvailableSeatsAndHour(seats, hour).stream()
                .map(availableMapper::toDomain)
                .collect(Collectors.toList());

    }

    @Override
    public List<Available> findBySeats(Integer seats) {
    return repository.findByAvailableSeatsGreaterThanEqual(seats).stream()
            .map(availableMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Available> findByHour(Integer hour) {
return repository.findByHour(hour).stream()
        .map(availableMapper::toDomain)
        .collect(Collectors.toList());

    }
}
