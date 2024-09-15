package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.UnprocessableEntityException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateReservationUseCase {

    private final ReservationRepository reservationRepository;
    private  final FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;
    private final UpdateAvailableUseCase updateAvailableUseCase;
    private final ReservationMapper mapper;

    public Reservation execute(Long reservationId, Reservation reservationUpdateRequest) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new UnprocessableEntityException("Reserva não encontrada"));

        Available availability = findAvailabilityByDataAndHourUseCase.execute(
                reservationUpdateRequest.getIdRestaurant(),
                reservationUpdateRequest.getReservationDate(),
                reservationUpdateRequest.getReservationHour()
        );

        if (availability.getAvailableSeats() < reservationUpdateRequest.getSeatsReserved()) {
            throw new UnprocessableEntityException("Não há assentos suficientes disponíveis");
        }

        updateAvailableUseCase.execute(reservation.getIdRestaurant(), (reservationUpdateRequest.getSeatsReserved() * -1));

        return mapper.toDomain(
                reservationRepository.save(mapper.update(reservationUpdateRequest, reservation))
        );
    }
}
