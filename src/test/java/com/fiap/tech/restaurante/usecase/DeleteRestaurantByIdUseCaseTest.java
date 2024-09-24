package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.enums.ReservationStatus;
import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.domain.useCase.reservation.FindReservationUseCase;
import com.fiap.tech.restaurante.domain.useCase.restaurant.DeleteRestaurantByIdUseCase;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteRestaurantByIdUseCaseTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private FindReservationUseCase findReservationUseCase;

    @InjectMocks
    private DeleteRestaurantByIdUseCase deleteRestaurantByIdUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteRestaurantWhenNoConfirmedReservations() {
        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);

        when(findReservationUseCase.execute(restaurantId, LocalDate.now(), null, ReservationStatus.CONFIRMED))
                .thenReturn(Collections.emptyList());

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));

        deleteRestaurantByIdUseCase.execute(restaurantId);

        verify(restaurantRepository, times(1)).delete(restaurantEntity);
    }

    @Test
    void shouldThrowExceptionWhenThereAreConfirmedReservations(){
        Long restaurantId = 1L;

        when(findReservationUseCase.execute(restaurantId, LocalDate.now(), null, ReservationStatus.CONFIRMED))
                .thenReturn(List.of(new Reservation()));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            deleteRestaurantByIdUseCase.execute(restaurantId);
        });

        assertEquals("Apenas restaurantes sem reserva confirmada para a data de hoje podem ser excluídos", exception.getMessage());

        verify(restaurantRepository, never()).delete(any(RestaurantEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        Long restaurantId = 1L;
        when(findReservationUseCase.execute(restaurantId, LocalDate.now(), null, ReservationStatus.CONFIRMED))
                .thenReturn(Collections.emptyList());

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            deleteRestaurantByIdUseCase.execute(restaurantId);
        });

        assertEquals("Restaurante não encontrado", exception.getMessage());

        verify(restaurantRepository, never()).delete(any(RestaurantEntity.class));
    }
}
