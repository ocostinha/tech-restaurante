package com.fiap.tech.restaurante.domain.useCase.reservation;

import com.fiap.tech.restaurante.domain.exception.BusinessException;
import com.fiap.tech.restaurante.domain.mappers.ReservationMapper;
import com.fiap.tech.restaurante.domain.model.Available;
import com.fiap.tech.restaurante.domain.model.Reservation;
import com.fiap.tech.restaurante.domain.useCase.availability.FindAvailabilityByDataAndHourUseCase;
import com.fiap.tech.restaurante.domain.useCase.availability.UpdateAvailableUseCase;
import com.fiap.tech.restaurante.infra.entity.ReservationEntity;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.ReservationRepository;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateReservationUseCaseTest {

    @InjectMocks
    private CreateReservationUseCase createReservationUseCase;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FindAvailabilityByDataAndHourUseCase findAvailabilityByDataAndHourUseCase;

    @Mock
    private UpdateAvailableUseCase updateAvailableUseCase;

    @Mock
    private ReservationMapper reservationMapper;

    private Reservation reservationRequest;
    private ReservationEntity reservationEntity;
    private Available available;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservationRequest = Reservation.builder()
                .idRestaurant(1L)
                .reservationOwnerName("Owner")
                .reservationOwnerEmail("owner@test.com")
                .seatsReserved(4)
                .build();

        reservationEntity = ReservationEntity.builder()
                .idRestaurant(1L)
                .reservationOwnerName("Owner")
                .reservationOwnerEmail("owner@test.com")
                .seatsReserved(4)
                .build();

        available = new Available(1L, 1L, null, null, 10, null, null);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Restaurant Test");
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(), any())).thenReturn(available);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
        when(reservationMapper.toEntity(any(Reservation.class))).thenReturn(reservationEntity);
        when(reservationMapper.toDomain(any(ReservationEntity.class))).thenReturn(reservationRequest);

        Reservation result = createReservationUseCase.execute(reservationRequest);

        assertNotNull(result);
        assertEquals("Owner", result.getReservationOwnerName());

        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase).execute(anyLong(), any(), any());
        verify(reservationRepository).save(any(ReservationEntity.class));
        verify(updateAvailableUseCase).execute(anyLong(), anyInt(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase, never()).execute(anyLong(), any(), any());
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
        verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenNoSeatsAvailable() {
        available.setAvailableSeats(2);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(findAvailabilityByDataAndHourUseCase.execute(anyLong(), any(), any())).thenReturn(available);

        assertThrows(BusinessException.class, () -> createReservationUseCase.execute(reservationRequest));

        verify(restaurantRepository).findById(1L);
        verify(findAvailabilityByDataAndHourUseCase).execute(anyLong(), any(), any());
        verify(reservationRepository, never()).save(any(ReservationEntity.class));
        verify(updateAvailableUseCase, never()).execute(anyLong(), anyInt(), any(), any());
    }
}
