package com.fiap.tech.restaurante.repository;
import com.fiap.tech.restaurante.repository.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByName(String name);
}
