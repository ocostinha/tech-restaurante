package com.fiap.tech.restaurante.usecase;

import com.fiap.tech.restaurante.domain.mappers.RestaurantMapper;
import com.fiap.tech.restaurante.domain.model.Restaurant;
import com.fiap.tech.restaurante.domain.useCase.restaurant.CreateRestaurantUseCase;
import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import com.fiap.tech.restaurante.infra.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = Restaurant.builder()
                .name("TacoLoco")
                .address("Rua Tuiuti")
                .number(42)
                .neighborhood("Tatuapé")
                .city("São Paulo")
                .state("SP")
                .cuisineType("Mexicano")
                .openAt(LocalTime.of(10, 0))
                .closeAt(LocalTime.of(22, 0))
                .intervalStart(LocalTime.of(15, 0))
                .intervalFinish(LocalTime.of(16, 0))
                .numberOfSeats(50)
                .build();

        restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("TacoLoco")
                .address("Rua Tuiuti")
                .number(42)
                .neighborhood("Tatuapé")
                .city("São Paulo")
                .state("SP")
                .cuisineType("Mexicano")
                .openAt(LocalTime.of(10, 0))
                .closeAt(LocalTime.of(22, 0))
                .intervalStart(LocalTime.of(15, 0))
                .intervalFinish(LocalTime.of(16, 0))
                .numberOfSeats(50)
                .build();
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Arrange
        when(restaurantRepository.findByName(restaurant.getName())).thenReturn(Optional.empty());
        when(restaurantMapper.toEntity(any(Restaurant.class))).thenReturn(restaurantEntity);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
        when(restaurantMapper.toDomain(any(RestaurantEntity.class))).thenReturn(restaurant);

        // Act
        Restaurant result = createRestaurantUseCase.execute(restaurant);

        // Assert
        assertNotNull(result);
        assertEquals(restaurant.getName(), result.getName());

        verify(restaurantRepository).findByName(restaurant.getName());
        verify(restaurantRepository, times(1)).save(restaurantEntity);
        verify(restaurantMapper).toEntity(restaurant);
        verify(restaurantMapper).toDomain(restaurantEntity);
    }
}
