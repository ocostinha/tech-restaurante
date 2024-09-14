package com.fiap.tech.restaurante.core.service;
import com.fiap.tech.restaurante.commoms.exception.BusinessException;
import com.fiap.tech.restaurante.commoms.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.core.domain.Restaurant;
import com.fiap.tech.restaurante.repository.RestaurantRepository;
import com.fiap.tech.restaurante.repository.entities.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper mapper;

    public List<RestaurantResponseDTO> findRestaurants(String name, String neighborhood, String city, String state, String type) {
        Specification<RestaurantEntity> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (neighborhood != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("neighborhood"), "%" + neighborhood + "%"));
        }
        if (city != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), "%" + city + "%"));
        }
        if (state != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), state));
        }
        if (type != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("type"), "%" + type + "%"));
        }

        List<RestaurantEntity> restaurants = restaurantRepository.findAll(spec);
        return restaurants.stream().map(mapper::toResponseDTO).toList();
    }

}

