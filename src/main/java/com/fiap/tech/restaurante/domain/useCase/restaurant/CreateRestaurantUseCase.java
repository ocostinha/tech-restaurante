package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateRestaurantUseCase {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper mapper;

    public Restaurant execute(Restaurant restaurant) {
        checkBusinessRules(restaurant.getName());

        return mapper.toDomain(
                restaurantRepository.save(
                        mapper.toEntity(restaurant)
                )
        );
    }

    private void checkBusinessRules(String restaurantName) {
        if (restaurantRepository.findByName(restaurantName).isPresent()) {
            throw new BusinessException("Já existe um restaurante com esse nome na plataforma.");
        }
    }
}

