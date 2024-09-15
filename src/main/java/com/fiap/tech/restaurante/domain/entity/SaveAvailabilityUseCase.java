package com.fiap.tech.restaurante.domain.entity;

import com.fiap.tech.restaurante.domain.repository.AvailableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveAvailabilityUseCase {
    private  final AvailableRepository availableRepository;

    public void save(Available available){
        availableRepository.save(available);
    }
}
