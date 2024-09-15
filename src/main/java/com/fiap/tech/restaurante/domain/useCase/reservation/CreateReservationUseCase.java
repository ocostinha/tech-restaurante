package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CreateReservationUseCase {

    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private  final FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;
    private final UpdateAvailableUseCase updateAvailableUseCase;
    private final ReservationMapper mapper;

    public Reservation execute(Reservation reservationRequest) {
        restaurantRepository.findById(reservationRequest.getIdRestaurant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado"));

        Available availability = findAvailabilityByDataAndHourUseCase.execute(
                reservationRequest.getIdRestaurant(),
                reservationRequest.getReservationDate(),
                reservationRequest.getReservationHour()
        );

        if (availability.getAvailableSeats() < reservationRequest.getSeatsReserved()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não há assentos disponíveis para o horário solicitado.");
        }

        Reservation reservation = mapper.toDomain(
                reservationRepository.save(
                        mapper.toEntity(reservationRequest)
                )
        );

        updateAvailableUseCase.execute(reservation.getIdRestaurant(), (reservation.getSeatsReserved() * -1));

        return reservation;
    }
}
