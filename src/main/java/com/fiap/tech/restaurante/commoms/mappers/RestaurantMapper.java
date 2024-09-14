package com.fiap.tech.restaurante.commoms.mappers;
import com.fiap.tech.restaurante.controller.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.controller.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.repository.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import static com.fiap.tech.restaurante.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "openAt", source = "openAt")
    @Mapping(target = "closeAt", source = "closeAt")
    Restaurant toDomain(RestaurantRequestDTO dto);

    RestaurantResponseDTO toResponse(Restaurant domain);

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    Restaurant toEntity(Restaurant domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "openAt", source = "openAt")
    @Mapping(target = "closeAt", source = "closeAt")
    Restaurant update(RestaurantRequestDTO dto, @MappingTarget Restaurant entity);
}

