package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.AvailableResponseDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.AvailableMapper;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Consultar disponibilidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade do restaurante",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class)) })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableResponseDTO> findAvailability(
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) LocalTime hour,
            @RequestParam Long idRestaurant
            ) {

        return findAvailabilityUseCase.execute(seats, hour, idRestaurant).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
