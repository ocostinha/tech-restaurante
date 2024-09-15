package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.AvailableResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.AvailableMapper;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/available/find")
@AllArgsConstructor
public class AvailableController {

    private final FindAvailabilityUseCase findAvailabilityUseCase;
    private  final AvailableMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableResponseDTO> findAvailability(
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) LocalTime hour) {

        return findAvailabilityUseCase.execute(seats, hour).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
