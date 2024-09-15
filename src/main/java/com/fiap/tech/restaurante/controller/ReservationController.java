package com.fiap.tech.restaurante.controller;

import com.fiap.tech.restaurante.dto.ReservationResponse;
import com.fiap.tech.restaurante.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/available/reserv_request")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.cancelReservation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
