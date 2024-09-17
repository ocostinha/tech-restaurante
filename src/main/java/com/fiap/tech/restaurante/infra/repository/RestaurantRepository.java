package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends
        JpaRepository<RestaurantEntity, Long>, JpaSpecificationExecutor<RestaurantEntity> {
    Optional<RestaurantEntity> findByName(String name);
}
