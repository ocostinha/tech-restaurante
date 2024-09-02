package com.fiap.tech.restaurante.controller;

import com.fiap.tech.restaurante.model.Reservation;
import com.fiap.tech.restaurante.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/available/reserv_request")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PutMapping("/{id}/complete")
    public ResponseEntity<Reservation> completeReservation(@PathVariable Long id) {
        Optional<Reservation> completedReservation = reservationService.completeReservation(id);

        return completedReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
