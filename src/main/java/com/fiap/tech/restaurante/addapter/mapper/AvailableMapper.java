package com.fiap.tech.restaurante.addapter.mapper;

import com.fiap.tech.restaurante.addapter.dto.AvailableDTO;
import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.infrastructure.entity.AvailableEntity;
import org.springframework.stereotype.Component;

@Component
public class AvailableMapper {

    public Available toDomain(AvailableEntity entity){
        return  new Available(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getDate(),
                entity.getHour(),
                entity.getAvailableSeats(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public  AvailableEntity toEntity(Available available){
        return  new AvailableEntity(
                available.getId(),
                available.getRestaurantId(),
                available.getDate(),
                available.getHour(),
                available.getAvailableSeats(),
                available.getCreatedAt(),
                available.getUpdatedAt()
        );
    }

    public AvailableDTO toDTO(Available available) {
        return  new AvailableDTO(
                available.getRestaurantId(),
                available.getDate(),
                available.getHour(),
                available.getAvailableSeats()
        );
    }
}
