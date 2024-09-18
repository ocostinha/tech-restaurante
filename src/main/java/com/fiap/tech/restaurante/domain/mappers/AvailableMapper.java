package com.fiap.tech.restaurante.domain.mappers;

import com.fiap.tech.restaurante.application.dto.AvailableResponseDTO;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AvailableMapper {
    Available toDomain(AvailableEntity entity);

    AvailableResponseDTO toResponseDTO(Available domain);

    AvailableEntity toEntity(Available domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AvailableEntity update(Available domain, @MappingTarget AvailableEntity entity);
}
