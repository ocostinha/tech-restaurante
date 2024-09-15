package com.fiap.tech.restaurante.addapter.mapper;

import com.fiap.tech.restaurante.addapter.dto.ResponseReservationDTO;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.infrastructure.entity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.getId(),
entity.getRestaurantId(),
                entity.getReservationOwnerName(),
                entity.getReservationOwnerEmail(),
                entity.getReservationDate(),
                        entity.getReservationHour(),
                        entity.getStatus(),
                        entity.getNumberOfSeats(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getRestaurantId(),
                reservation.getReservationOwnerName(),
                reservation.getReservationOwnerEmail(),
                reservation.getReservationDate(),
                reservation.getReservationHour(),
                reservation.getNumberOfSeats(),
                reservation.getStatus(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
    }

    public ResponseReservationDTO toDTO(Reservation reservation){
        return  new ResponseReservationDTO(
                reservation.getId(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
    }


}