package com.fiap.tech.restaurante.repository;
import com.fiap.tech.restaurante.repository.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);
}
