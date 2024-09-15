package com.fiap.tech.restaurante.domain.mappers;

import com.fiap.tech.restaurante.application.dto.ReservationRequestDTO;
import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.fiap.tech.restaurante.domain.mappers.utils.MappingUtils.PENDING_STATUS;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationResponseDTO toResponseDTO (Reservation domain);

    Reservation toDomain (ReservationEntity entity);

    @Mapping(target = "status", expression = PENDING_STATUS)
    Reservation toDomain (ReservationRequestDTO dto);

    ReservationEntity toEntity (Reservation domain);
}
