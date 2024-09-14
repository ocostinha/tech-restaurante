package com.fiap.tech.restaurante.core.service;
import com.fiap.tech.restaurante.commoms.exception.BusinessException;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.repository.RestaurantRepository;
import com.fiap.tech.restaurante.repository.entities.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO requestDTO) throws Exception {
        validateRequest(requestDTO);
        checkBusinessRules(requestDTO);

        Restaurant restaurant = mapToEntity(requestDTO);
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant = restaurantRepository.save(restaurant);

        return new RestaurantResponseDTO(restaurant.getId(), restaurant.getCreatedAt());
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

    private Restaurant mapToEntity(RestaurantRequestDTO requestDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(requestDTO.name);
        restaurant.setAddress(requestDTO.address);
        restaurant.setNumber(requestDTO.number);
        restaurant.setNeighborhood(requestDTO.neighborhood);
        restaurant.setCity(requestDTO.city);
        restaurant.setState(requestDTO.state);
        restaurant.setType(requestDTO.type);
        restaurant.setOpenAt(requestDTO.openAt);
        restaurant.setCloseAt(requestDTO.closeAt);
        restaurant.setIntervalStart(requestDTO.intervalStart);
        restaurant.setIntervalFinish(requestDTO.intervalFinish);
        restaurant.setNumberOfSeats(requestDTO.numberOfSeats);
        return restaurant;
    }
}

