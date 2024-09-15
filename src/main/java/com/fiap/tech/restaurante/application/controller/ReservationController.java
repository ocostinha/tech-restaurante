package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.ReservationRequestDTO;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.useCase.reservation.CancelReservationUseCase;
import com.fiap.tech.restaurante.domain.useCase.reservation.CompleteReservationUseCase;
import com.fiap.tech.restaurante.domain.useCase.reservation.CreateReservationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/available/reserve_request")
@RequiredArgsConstructor
public class ReservationController {

    private final CancelReservationUseCase cancelReservationUseCase;
    private final CompleteReservationUseCase completeReservationUseCase;
    private final CreateReservationUseCase createReservationUseCase;
    private final ReservationMapper mapper;

    @PostMapping("/reserve_request")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequest) {
        return mapper.toResponseDTO(
                createReservationUseCase.execute(
                        mapper.toDomain(reservationRequest)
                )
        );
    }

    @PutMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO completeReservation(@PathVariable Long id) {
        return mapper.toResponseDTO(completeReservationUseCase.execute(id));
    }

    @DeleteMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO cancelReservation(@PathVariable Long id) {
        return mapper.toResponseDTO(cancelReservationUseCase.execute(id));
    }
}
