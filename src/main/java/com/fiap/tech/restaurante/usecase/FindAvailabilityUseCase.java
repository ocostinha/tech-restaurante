package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.domain.repository.AvailableRepository;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindAvailabilityUseCase {

    private final AvailableRepository availableRepository;

    public Available findAvailabilityRestaurantIdAndDateAndHour(UUID restaurantId, LocalDate reservationDate, int reservationHour) {
    return availableRepository.findAvailability(restaurantId, reservationDate, reservationHour)
            .orElseThrow(() -> new UnprocessableEntityException("Nenhuma disponibilidade encontrada para a data e hora selecionadas"));
}

    public List<Available> findAvailability(Integer seats, Integer hour) {
        if (seats == null && hour == null) {
            return availableRepository.findAll();
        }else if (seats != null && hour != null) {
            return availableRepository.findBySeatsAndHour(seats, hour);
        }else if (seats != null) {
            return availableRepository.findBySeats(seats);
        }

        return availableRepository.findByHour(hour);
    }
}
