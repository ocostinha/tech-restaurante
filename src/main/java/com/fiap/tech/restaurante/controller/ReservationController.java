package com.fiap.tech.restaurante.controller;

import com.fiap.tech.restaurante.dto.ReservationRequestDto;
import com.fiap.tech.restaurante.dto.ReservationResponseDto;
import com.fiap.tech.restaurante.service.ReservationService;
import com.fiap.tech.restaurante.exception.UnavailableSeatsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/available")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserv_request")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody @Valid ReservationRequestDto reservationRequest) {
        try {
            ReservationResponseDto response = reservationService.createReservation(reservationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UnavailableSeatsException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ReservationResponseDto(e.getMessage()));
        }
    }
}
