package com.fiap.tech.restaurante.service;

import com.fiap.tech.restaurante.dto.ReservationRequestDto;
import com.fiap.tech.restaurante.dto.ReservationResponseDto;
import com.fiap.tech.restaurante.exception.UnavailableSeatsException;
import com.fiap.tech.restaurante.model.Reservation;
import com.fiap.tech.restaurante.model.Restaurant;
import com.fiap.tech.restaurante.repository.ReservationRepository;
import com.fiap.tech.restaurante.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final AvailabilityService availabilityService;

    public ReservationResponseDto createReservation(ReservationRequestDto request) {
        Restaurant restaurant = restaurantRepository.findById(request.getIdRestaurant())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado"));

        boolean isAvailable = availabilityService.checkAvailability(
                restaurant,
                request.getReservationDate(),
                request.getReservationHour(),
                request.getNumberOfSeats()
        );

        if (!isAvailable) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não há assentos disponíveis para o horário solicitado.");
        }

        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .reservationOwnerName(request.getReservationOwnerName())
                .reservationOwnerEmail(request.getReservationOwnerEmail())
                .reservationDate(request.getReservationDate())
                .reservationHour(request.getReservationHour())
                .numberOfSeats(request.getNumberOfSeats())
                .status("RESERVED")
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        availabilityService.updateAvailability(restaurant, request.getNumberOfSeats());

        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt(),
                "Reserva criada com sucesso!"
        );
    }
}
