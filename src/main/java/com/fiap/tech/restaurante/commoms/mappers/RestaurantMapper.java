package com.fiap.tech.restaurante.commoms.mappers;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.core.domain.Restaurant;
import com.fiap.tech.restaurante.repository.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import static com.fiap.tech.restaurante.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "openAt", source = "openAt")
    @Mapping(target = "closeAt", source = "closeAt")
    Restaurant toDomain(RestaurantRequestDTO dto);

    Restaurant toResponse(RestaurantEntity entity);

    RestaurantResponseDTO toResponseDTO(RestaurantEntity entity);

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    RestaurantEntity toEntity(Restaurant domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "openAt", source = "openAt")
    @Mapping(target = "closeAt", source = "closeAt")
    RestaurantEntity update(RestaurantRequestDTO dto, @MappingTarget RestaurantEntity entity);
}