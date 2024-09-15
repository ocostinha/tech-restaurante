package com.fiap.tech.restaurante.addapter.controller;

import com.fiap.tech.restaurante.addapter.dto.AvailableDTO;
import com.fiap.tech.restaurante.addapter.mapper.AvailableMapper;
import com.fiap.tech.restaurante.usecase.FindAvailabilityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<AvailableDTO> findAvailability(
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) Integer hour) {

        return findAvailabilityUseCase.findAvailability(seats, hour).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
