package com.fiap.tech.restaurante.domain.useCase.availability;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import com.fiap.tech.restaurante.infra.repository.AvailableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAvailableUseCase {

    private final AvailableRepository availableRepository;

    public void execute(Long idRestaurant, Integer seatsToAdd) {
        AvailableEntity seatEntity = availableRepository.findByIdRestaurant(idRestaurant)
                .orElseThrow(() -> new ResourceNotFoundException("Available not found"));

        seatEntity.setAvailableSeats(seatEntity.getAvailableSeats() + seatsToAdd);
        availableRepository.save(seatEntity);
    }
}
