package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateRestaurantUseCase {

    private RestaurantRepository restaurantRepository;
    private RestaurantMapper mapper;

    public Restaurant execute(Long idRestaurant, Restaurant restaurant) {
        checkBusinessRules(restaurant.getName());

        return mapper.toDomain(
                restaurantRepository.save(
                        mapper.update(
                                restaurant,
                                restaurantRepository.findById(idRestaurant)
                                        .orElseThrow(() -> new BusinessException("Restaurante não encontrado"))
                        )
                )
        );
    }

    private void checkBusinessRules(String name) {
        if (restaurantRepository.findByName(name).isPresent()) {
            throw new BusinessException("Já existe um restaurante com esse nome na plataforma.");
        }
    }
}

