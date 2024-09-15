package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.useCase.restaurant.CreateRestaurantUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final RestaurantMapper mapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponseDTO createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        return mapper.toResponse(
                createRestaurantUseCase.execute(mapper.toDomain(requestDTO))
        );
    }
}