package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.exception.ResourceNotFoundException;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindRestaurantByIdUseCase {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper mapper;

    public Restaurant execute(Long id) {
        return mapper.toDomain(
                restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"))
        );
    }
}

