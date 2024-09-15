package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.seats.CheckAvailableSeatUseCase;
import com.fiap.tech.restaurante.domain.useCase.seats.UpdateAvailableSeatUseCase;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
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
    private final CheckAvailableSeatUseCase checkAvailableSeatUseCase;
    private final UpdateAvailableSeatUseCase updateAvailableSeatUseCase;
    private final ReservationMapper mapper;

    public Reservation execute(Reservation reservationRequest) {
        RestaurantEntity restaurant = restaurantRepository.findById(reservationRequest.getIdRestaurant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado"));

        boolean isAvailable = checkAvailableSeatUseCase.execute(
                restaurant.getId(),
                reservationRequest.getReservationDate(),
                reservationRequest.getReservationHour(),
                reservationRequest.getSeatsReserved()
        );

        if (!isAvailable) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não há assentos disponíveis para o horário solicitado.");
        }

        Reservation reservation = mapper.toDomain(
                reservationRepository.save(
                        mapper.toEntity(reservationRequest)
                )
        );

        updateAvailableSeatUseCase.execute(reservation.getIdRestaurant(), (reservation.getSeatsReserved() * -1));

        return reservation;
    }
}
