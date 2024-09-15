package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.AvailableMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class FindAvailabilityByDataAndHourUseCase {

    private final AvailableRepository availableRepository;
    private final AvailableMapper mapper;

    public Available execute(
            Long idRestaurant,
            LocalDate reservationDate,
            LocalTime reservationHour
    ) {
        return mapper.toDomain(
                availableRepository.findByIdRestaurantAndDateAndHour(idRestaurant, reservationDate, reservationHour)
                        .orElseThrow(() -> new UnprocessableEntityException("Nenhuma disponibilidade encontrada para a data e hora selecionadas"))
        );
    }
}
