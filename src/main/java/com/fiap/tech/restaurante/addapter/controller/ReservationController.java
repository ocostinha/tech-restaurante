package com.fiap.tech.restaurante.addapter.controller;

import com.fiap.tech.restaurante.addapter.dto.RequestReservationDTO;
import com.fiap.tech.restaurante.addapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.addapter.mapper.ReservationMapper;
import com.fiap.tech.restaurante.usecase.UpdateReservationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/available/reserv_request")
@AllArgsConstructor
public class ReservationController {

    private final UpdateReservationUseCase updateReservationUseCase;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseReservationDTO updateReservation(
            @PathVariable UUID id,
            @RequestBody RequestReservationDTO request) {
        return  new ReservationMapper().toDTO(
                updateReservationUseCase.updateReservation(
                        id,
                        request.getRestaurantId(),
                        request.getReservationOwnerName(),
                        request.getReservationOwnerEmail(),
                        request.getReservationDate(),
                        request.getReservationHour(),
                        request.getNumberOfSeats()
                ));
    }

}
