package com.fiap.tech.restaurante.core.service;
import com.fiap.tech.restaurante.commoms.exception.BusinessException;
import com.fiap.tech.restaurante.commoms.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.core.domain.Restaurant;
import com.fiap.tech.restaurante.repository.RestaurantRepository;
import com.fiap.tech.restaurante.repository.entities.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper mapper;

    public Restaurant editRestaurant(Long id, RestaurantRequestDTO requestDTO) throws Exception {
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalArgumentException("Restaurante não encontrado.");
        }

        validateRequest(requestDTO);
        checkBusinessRules(requestDTO);

        RestaurantEntity restaurant = optionalRestaurant.get();
        mapper.update(requestDTO, restaurant);
        restaurant = restaurantRepository.save(restaurant);

        return mapper.toResponse(restaurant);
    }

    private void validateRequest(RestaurantRequestDTO requestDTO) throws Exception {
        if (requestDTO.name == null || requestDTO.address == null || requestDTO.numberOfSeats < 3 || requestDTO.openAt == null || requestDTO.closeAt == null) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios e tenha no mínimo 3 assentos.");
        }

        if (requestDTO.openAt.isAfter(requestDTO.closeAt) && requestDTO.closeAt.isBefore(LocalTime.MIDNIGHT)) {
            throw new IllegalArgumentException("A hora de fechamento não pode ser anterior à de abertura.");
        }
    }

    private void checkBusinessRules(RestaurantRequestDTO requestDTO) throws Exception {
        if (restaurantRepository.findByName(requestDTO.name).isPresent()) {
            throw new BusinessException("Já existe um restaurante com esse nome na plataforma.");
        }
    }
}

