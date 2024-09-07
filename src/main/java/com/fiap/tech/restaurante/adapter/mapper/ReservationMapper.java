package com.fiap.tech.restaurante.adapter.mapper;

import com.fiap.tech.restaurante.adapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.infrastructure.entity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.getId(),
                entity.getCreationDate(),
                entity.getUpdateDate()
        );
    }

    public ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getCreationDate(),
                reservation.getUpdateDate()
        );
    }

public ResponseReservationDTO toDto(Reservation reservation){
        return  new ResponseReservationDTO(
                reservation.getId(),
                reservation.getCreationDate(),
                reservation.getUpdateDate()
        );
}



}