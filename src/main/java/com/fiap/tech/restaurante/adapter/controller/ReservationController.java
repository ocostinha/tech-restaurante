package com.fiap.tech.restaurante.adapter.controller;

import com.fiap.tech.restaurante.adapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.adapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.usecase.FindReservationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final FindReservationUseCase useCase;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseReservationDTO findReservationById(@PathVariable UUID id) {
        return new ReservationMapper().toDto(useCase.findReservation(id));
    }
}
