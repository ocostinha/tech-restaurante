package com.fiap.tech.restaurante.domain.useCase.seats;

import com.fiap.tech.restaurante.infra.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CheckAvailableSeatUseCase {

    private final SeatRepository seatRepository;

    public boolean execute(
            Long idRestaurant,
            LocalDate reservationDate,
            LocalTime reservationTime,
            Integer numberOfSeats
    ) {
        // TODO create check available seats role
        return true;
    }
}
