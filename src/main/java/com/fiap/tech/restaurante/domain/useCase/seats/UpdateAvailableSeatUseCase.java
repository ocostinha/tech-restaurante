package com.fiap.tech.restaurante.domain.useCase.seats;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.infra.entity.SeatEntity;
import com.fiap.tech.restaurante.infra.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAvailableSeatUseCase {

    private final SeatRepository seatRepository;

    public void execute(Long idRestaurant, Integer seatsToAdd) {
        SeatEntity seatEntity = seatRepository.findByIdRestaurant(idRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        seatEntity.setAvailableSeats(seatEntity.getAvailableSeats() + seatsToAdd);
        seatRepository.save(seatEntity);
    }
}
