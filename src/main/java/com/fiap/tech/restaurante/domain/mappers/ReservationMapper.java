package com.fiap.tech.restaurante.domain.mappers;

import com.fiap.tech.restaurante.application.dto.ReservationResponseDTO;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationResponseDTO toResponseDTO (Reservation domain);
    Reservation toDomain (ReservationEntity entity);
    ReservationEntity toEntity (Reservation domain);
}
