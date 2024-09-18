package com.fiap.tech.restaurante.infra.repository;

import com.fiap.tech.restaurante.infra.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, UUID> {
    List<EvaluationEntity> findByIdRestaurant(Long idRestaurant);

    List<EvaluationEntity> findByIdReserve(UUID idReserve);
}
