package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteRestaurantByIdUseCase {

    private RestaurantRepository restaurantRepository;

    public void execute(Long id) {
        restaurantRepository.delete(
                restaurantRepository.findById(id).orElseThrow(() -> new BusinessException("Restaurante não encontrado"))
        );
    }
}

