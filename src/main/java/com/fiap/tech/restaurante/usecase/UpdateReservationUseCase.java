package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.entity.Available;
import com.fiap.tech.restaurante.domain.entity.Reservation;
import com.fiap.tech.restaurante.domain.entity.SaveAvailabilityUseCase;
import com.fiap.tech.restaurante.domain.repository.ReservationRepository;
import com.fiap.tech.restaurante.exception.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateReservationUseCase {

    private final ReservationRepository reservationRepository;
    private  final FindAvailabilityUseCase findAvailabilityUseCase;
    private  final SaveAvailabilityUseCase saveAvailabilityUseCase;

    public Reservation updateReservation(UUID reservationId, UUID restaurantId, String reservationOwnerName,
                                         String reservationOwnerEmail, LocalDate reservationDate, int reservationHour,
                                         int numberOfSeats) throws UnprocessableEntityException {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new UnprocessableEntityException("Reserva não encontrada"));
        Available availability = findAvailabilityUseCase.findAvailabilityRestaurantIdAndDateAndHour(restaurantId, reservationDate, reservationHour);

        if (availability.getAvailableSeats() < numberOfSeats) {
            throw new UnprocessableEntityException("Não há assentos suficientes disponíveis");
        }

        reservation.updateReservation(reservationOwnerName, reservationOwnerEmail, reservationDate, reservationHour, numberOfSeats);
        reservationRepository.save(reservation);
        availability.subtractSeats(numberOfSeats);
saveAvailabilityUseCase.save(availability);
        return reservation;
    }
}
