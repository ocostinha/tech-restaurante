package com.fiap.tech.restaurante.service;

import com.fiap.tech.restaurante.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.model.Seat;
import com.fiap.tech.restaurante.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public void updateAvailableSeats(Integer seatsToAdd) {
        Seat seat = seatRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        seat.setAvailableSeats(seat.getAvailableSeats() + seatsToAdd);
        seatRepository.save(seat);
    }
}
