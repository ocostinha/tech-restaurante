package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.AvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailableRepository extends JpaRepository<AvailableEntity, UUID> {

	Optional<AvailableEntity> findByIdRestaurantAndDateAndHour(Long restaurantId, LocalDate date, LocalTime hour);

	List<AvailableEntity> findByAvailableSeatsGreaterThanEqualAndHourAndIdRestaurant(int seats, LocalTime hour,
			Long idRestaurant);

	List<AvailableEntity> findByAvailableSeatsGreaterThanEqualAndIdRestaurant(int seats, Long idRestaurant);

	List<AvailableEntity> findByHourAndIdRestaurant(LocalTime hour, Long idRestaurant);

	List<AvailableEntity> findByIdRestaurant(Long idRestaurant);

}
