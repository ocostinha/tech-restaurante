package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.useCase.reservation.CancelReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/available/reserve_request")
@RequiredArgsConstructor
public class ReservationController {

    private final CancelReservationUseCase cancelReservationUseCase;
    private final ReservationMapper mapper;

    @DeleteMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO cancelReservation(@PathVariable Long id) {
        return mapper.toResponseDTO(cancelReservationUseCase.execute(id));
    }
}
