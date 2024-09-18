package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FindReservationUseCase {

    private ReservationRepository reservationRepository;
    private ReservationMapper mapper;

    public List<Reservation> execute(
            Long idRestaurant,
            LocalDate date,
            LocalTime hour,
            ReservationStatus status
    ) {
        Specification<ReservationEntity> spec = Specification.where(null);

        if (idRestaurant != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idRestaurant"), idRestaurant));
        }
        if (date != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("reservationDate"), date));
        }
        if (hour != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("reservationHour"), hour));
        }
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status));
        }

        return reservationRepository.findAll(spec).stream().map(mapper::toDomain).toList();
    }
}

