package com.fiap.tech.restaurante.domain.mappers;

import com.fiap.tech.restaurante.application.dto.EvaluationRequestDTO;
import com.fiap.tech.restaurante.application.dto.EvaluationResponseDTO;
import com.fiap.tech.restaurante.domain.model.Evaluation;
import com.fiap.tech.restaurante.infra.entity.EvaluationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

	Evaluation toDomain(EvaluationEntity entity);

	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	Evaluation toDomain(EvaluationRequestDTO entity);

	EvaluationResponseDTO toResponseDTO(Evaluation domain);

	EvaluationEntity toEntity(Evaluation domain);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	EvaluationEntity update(Evaluation domain, @MappingTarget EvaluationEntity entity);

}
