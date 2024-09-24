package com.fiap.tech.restaurante.domain.mappers;

import com.fiap.tech.restaurante.application.dto.RestaurantRequestDTO;
import com.fiap.tech.restaurante.application.dto.RestaurantResponseDTO;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	Restaurant toDomain(RestaurantRequestDTO dto);

	Restaurant toDomain(RestaurantEntity entity);

	RestaurantResponseDTO toResponse(Restaurant domain);

	RestaurantEntity toEntity(Restaurant domain);

	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "openAt", source = "openAt")
	@Mapping(target = "closeAt", source = "closeAt")
	RestaurantEntity update(Restaurant domain, @MappingTarget RestaurantEntity entity);

}
