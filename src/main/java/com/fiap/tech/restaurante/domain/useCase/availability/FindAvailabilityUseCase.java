package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.mappers.AvailableMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FindAvailabilityUseCase {

    private final AvailableRepository availableRepository;
    private final AvailableMapper mapper;

    public List<Available> execute(Integer seats, LocalTime hour) {
        if (seats == null && hour == null) {
            return availableRepository.findAll().stream().map(mapper::toDomain).toList();
        } else if (seats != null && hour != null) {
            return availableRepository.findByAvailableSeatsAndHour(seats, hour).stream().map(mapper::toDomain).toList();
        } else if (seats != null) {
            return availableRepository.findByAvailableSeatsGreaterThanEqual(seats).stream().map(mapper::toDomain).toList();
        }

        return availableRepository.findByHour(hour).stream().map(mapper::toDomain).toList();
    }
}
