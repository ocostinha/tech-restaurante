package com.fiap.tech.restaurante.domain.useCase.restaurant;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationUseCase;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DeleteRestaurantByIdUseCase {

    private final FindReservationUseCase findReservationUseCase;
    private final RestaurantRepository restaurantRepository;

    public void execute(Long id) {
        if (!findReservationUseCase.execute(id, LocalDate.now(), null, ReservationStatus.CONFIRMED).isEmpty()) {
            throw new BusinessException(
                    "Apenas restaurantes sem reserva confirmada para a data de hoje podem ser excluídos"
            );
        }

        restaurantRepository.delete(
                restaurantRepository.findById(id).orElseThrow(() -> new BusinessException("Restaurante não encontrado"))
        );
    }
}

