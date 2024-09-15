package com.fiap.tech.restaurante.application.controller;

import com.fiap.tech.restaurante.application.dto.ReservationRequestDTO;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.useCase.reservation.*;
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
    private final FindReservationUseCase findReservationUseCase;
    private final UpdateReservationUseCase updateReservationUseCase;
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

    @GetMapping("/{id}/find")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO findReservationById(@PathVariable Long id) {
        return  mapper.toResponseDTO(findReservationUseCase.findReservation(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO updateReservation(
            @PathVariable Long id,
            @RequestBody ReservationRequestDTO request) {
        return mapper.toResponseDTO(
                updateReservationUseCase.execute(id, mapper.toDomain(request))
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
